package com.openlab.hotel.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.openlab.hotel.base.Result;
import com.openlab.hotel.entity.SysUser;
import com.openlab.hotel.exception.BadRequestException;
import com.openlab.hotel.mapper.SysUserMapper;
import com.openlab.hotel.service.SysRoleService;
import com.openlab.hotel.service.SysUserService;
import com.openlab.hotel.util.Md5Util;
import com.openlab.hotel.util.PageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

//@CrossOrigin //TODO 后面去掉
@RestController
@RequestMapping("/user")
public class SysUserController {
    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysUserMapper sysUserMapper;

    @Operation(summary = "用户分页列表接口")
    @Parameters({
            @Parameter(name = "searchValue", in = ParameterIn.QUERY, description = "关键字搜索"),
            @Parameter(name = "status", in = ParameterIn.QUERY, description = "用户状态"),
            @Parameter(name = "pageSize", in = ParameterIn.QUERY, description = "每页显示行数", required = true),
            @Parameter(name = "pageIndex", in = ParameterIn.QUERY, description = "当前页码", required = true)
    })
    @GetMapping
    public Result<Object> list(@RequestParam(value = "searchValue", defaultValue = "") String searchValue,
                               @RequestParam(value = "status",defaultValue = "-1") int status,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                               @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex
                        ) {

        IPage<SysUser> page = new Page<>(pageIndex, pageSize);
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(status != -1, SysUser::getStatus, status);
        queryWrapper.like(!searchValue.isEmpty(), SysUser::getUsername, searchValue)
                .or()
                .like(!searchValue.isEmpty(), SysUser::getRealName, searchValue)
                .or()
                .eq(!searchValue.isEmpty(), SysUser::getSex, searchValue);
        sysUserService.page(page ,queryWrapper);
        for (SysUser record : page.getRecords()) {
           record.setUserRoleVo(sysUserMapper.getRoleByUserId(record.getRoleId()));
        }
        // 封装分页查询数据
        PageUtil<SysUser> users = new PageUtil<>(page.getCurrent(), page.getTotal(), page.getRecords());
        return Result.ok().data(users);
    }

    /**
     * 新增用户
     * @param sysUser 用户对象
     * @return 返回提示
     */
    @Operation(summary = "新增系统用户接口")
    @Parameter(name = "sysUser", in = ParameterIn.QUERY, description = "系统用户对象，用于接收前端提交的JSON格式的数据", required = true)
    @PostMapping
    public Result<Object> addUser(@RequestBody @Validated SysUser sysUser) {
        // 1. 根据登录名称来查询是否存在
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, sysUser.getUsername());
        SysUser dbSysUser = sysUserService.getOne(queryWrapper);
        if (Objects.nonNull(dbSysUser)) {
            // 表示该登录名已经存在，则返回错误信息
            return Result.fail().message("登录名已经存在，请重新输入！");
        }
        // 表示该登录名不存在，则添加
        sysUser.setPassword(Md5Util.Md5(sysUser.getPassword()));
        boolean flag = sysUserService.save(sysUser);
        if (flag) {
            return Result.ok().message("添加系统用户成功!");
        }
        return Result.fail().message("添加系统用户失败!");
    }

    /**
     * 根据用户编号查询用户详情
     * @param id 用户编号
     * @return 用户详情
     */
    @Operation(summary = "根据用户编号查询用户详情接口")
    @Parameter(name = "id", in = ParameterIn.PATH, description = "用户编号", required = true)
    @GetMapping("/{id}")
    public Result<Object> getUserById(@PathVariable("id") Long id) {
        SysUser sysUser = sysUserService.getById(id);
        if (Objects.isNull(sysUser)) {
            return Result.fail().message("用户不存在！");
        }
        return Result.ok().data(sysUser);
    }

    /**
     * 更新用户接口
     * @param sysUser 用户对象
     * @return 返回提示
     */
    @Operation(summary = "更新系统用户接口")
    @Parameter(name = "sysUser", in = ParameterIn.QUERY, description = "系统用户对象，用于接收前端提交的JSON格式的数据", required = true)
    @PutMapping
    public Result<Object> editUser(@RequestBody @Validated SysUser sysUser) {
        // 1. 根据登录名称来查询是否存在
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, sysUser.getUsername());
        SysUser dbSysUser = sysUserService.getOne(queryWrapper);
        if (Objects.isNull(dbSysUser)) {
            // 表示该登录名已经存在，则返回错误信息
            return Result.fail().message("登录名不存在，请重新输入！");
        }

        sysUser.setPassword(dbSysUser.getPassword());
        boolean flag = sysUserService.updateById(sysUser);
        if (flag) {
            return Result.ok().message("更新系统用户成功!");
        }
        return Result.fail().message("更新系统用户失败!");
    }

    /**
     * 删除系统用户
     * @param id 用户编号
     * @return 返回操作提示
     */
    @Operation(summary = "删除系统用户接口")
    @Parameter(name = "id", in = ParameterIn.PATH, description = "根据用户编号删除用户信息", required = true)
    @DeleteMapping("/{id}")
    public Result<Object> deleteUser(@PathVariable("id") Long id) {
        if (Objects.isNull(id)) {
            throw new BadRequestException("删除用户的编号不能为空！");
        }
        boolean flag = sysUserService.removeById(id);
        if (flag) {
            return Result.ok().message("删除系统用户成功!");
        }
        return Result.fail().message("删除系统用户失败!");
    }

}
