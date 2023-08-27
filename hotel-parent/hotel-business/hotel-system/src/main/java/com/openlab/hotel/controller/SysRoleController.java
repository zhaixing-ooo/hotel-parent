package com.openlab.hotel.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.openlab.hotel.base.Result;
import com.openlab.hotel.entity.SysRole;
import com.openlab.hotel.service.SysRoleService;
import com.openlab.hotel.util.PageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

//@CrossOrigin
@RestController
@RequestMapping("/role")
public class SysRoleController {
    @Resource
    private SysRoleService sysRoleService;
    @GetMapping
    public Result<Object> list(@RequestParam(value = "searchValue", defaultValue = "") String searchValue,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                               @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex
    ) {

        IPage<SysRole> page = new Page<>(pageIndex, pageSize);
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!searchValue.isEmpty(), SysRole::getName, searchValue)
                .or()
                .like(!searchValue.isEmpty(), SysRole::getCode, searchValue);
        sysRoleService.page(page ,queryWrapper);
        // 封装分页查询数据
        PageUtil<SysRole> roles = new PageUtil<>(page.getCurrent(), page.getTotal(), page.getRecords());
        return Result.ok().data(roles);
    }
    @Operation(summary = "添加角色接口")
    @PostMapping
    public Result<Object> addRole(@RequestBody @Validated SysRole sysRole){
        // 1. 根据登录名称来查询是否存在
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getName, sysRole.getName());
        SysRole dbSysRole = sysRoleService.getOne(queryWrapper);
        if (Objects.nonNull(dbSysRole)) {
            // 表示该登录名已经存在，则返回错误信息
            return Result.fail().message("角色名称已经存在，请重新输入！");
        }
        // 表示该登录名不存在，则添加
        boolean flag = sysRoleService.save(sysRole);
        if (flag) {
            return Result.ok().message("添加角色成功!");
        }
        return Result.fail().message("添加角色失败!");
    }
    @Operation(summary = "根据角色编号查询角色详情接口")
    @Parameter(name = "id", in = ParameterIn.PATH, description = "角色编号", required = true)
    @GetMapping("/{id}")
    public Result<Object> getRoleById(@PathVariable("id") Long id) {
        SysRole sysRole = sysRoleService.getById(id);
        if (Objects.isNull(sysRole)) {
            return Result.fail().message("角色不存在！");
        }
        return Result.ok().data(sysRole);
    }
    @Operation(summary = "更新系统角色接口")
    @Parameter(name = "sysRole", in = ParameterIn.QUERY, description = "系统角色对象，用于接收前端提交的JSON格式的数据", required = true)
    @PutMapping
    public Result<Object> editRole(@RequestBody @Validated SysRole sysRole) {
        // 1. 根据登录名称来查询是否存在
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getName, sysRole.getName());
        SysRole dbSysRole = sysRoleService.getOne(queryWrapper);
        if (Objects.isNull(dbSysRole)) {
            // 表示该登录名已经存在，则返回错误信息
            return Result.fail().message("登录名不存在，请重新输入！");
        }
        boolean flag = sysRoleService.updateById(sysRole);
        if (flag) {
            return Result.ok().message("更新系统用户成功!");
        }
        return Result.fail().message("更新系统用户失败!");
    }
    @Operation(summary = "删除系统角色接口")
    @Parameter(name = "id", in = ParameterIn.QUERY, description = "系统角色对象id", required = true)
    @DeleteMapping("/{id}")
    public Result<Object> deleteRoleById(@PathVariable("id") Long id){
        SysRole sysRole = sysRoleService.getById(id);
        if (Objects.isNull(sysRole)) {
            return Result.fail().message("角色不存在！");
        }
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getId,id);
        boolean flag = sysRoleService.remove(queryWrapper);
        if(!flag){
            return Result.fail().message("角色删除失败！");
        }
        return Result.ok().message("角色删除成功！");
    }
}
