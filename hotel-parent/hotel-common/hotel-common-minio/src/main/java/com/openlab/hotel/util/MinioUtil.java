package com.openlab.hotel.util;

import io.micrometer.common.util.StringUtils;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
public final class MinioUtil {
    /**
     * 查看存储bucket是否存在
     * @param client 客户端
     * @param bucketName 桶名称
     * @return 判断桶是否存在
     */
    public static Boolean bucketExists(MinioClient client, String bucketName) {
        try {
            return client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            throw new RuntimeException("查看存储bucket是否存在出错!" + e.getMessage());
        }
    }

    /**
     * 创建存储bucket
     * @param client 客户端
     * @param bucketName 桶名称
     */
    public static void makeBucket(MinioClient client, String bucketName) {
        try {
            client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            throw new RuntimeException("创建存储bucket出错!" + e.getMessage());
        }
    }
    /**
     * 删除存储bucket
     * @param client 客户端
     * @param bucketName 桶名称
     */
    public static void removeBucket(MinioClient client, String bucketName) {
        try {
            client.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            throw new RuntimeException("预览文件出错!" + e.getMessage());
        }
    }

    /**
     * 获取全部bucket
     * @param client 客户端
     * return 返回桶列表
     */
    public static List<Bucket> getAllBuckets(MinioClient client) {
        try {
            return client.listBuckets();
        } catch (Exception e) {
            throw new RuntimeException("获取全部bucket出错!" + e.getMessage());
        }
    }

    /**
     * 文件上传
     * @param client 客户端
     * @param bucketName 桶名称
     * @param file 上传文件对象
     * @return 返回文件路径
     */
    public static String upload(MinioClient client, String bucketName, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isBlank(originalFilename)){
            throw new RuntimeException("上传文件不能为空!");
        }
        String fileName = UUID.randomUUID().toString() + ext(file.getContentType());
        String objectName = LocalDate.now() + "/" + fileName;
        try {
            client.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build()); // 文件名称相同会覆盖
        } catch (Exception e) {
            throw new RuntimeException("上传文件出错!" + e.getMessage());
        }
        return objectName;
    }

    private static String ext(String miniType) {
        return switch (miniType) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            case "image/gif" -> ".gif";
            default -> throw new RuntimeException("上传图片只支持 gif、jpg 和 png 格式！");
        };
    }

    /**
     * 获取文件
     * @param client 客户端
     * @param bucketName 桶名称
     * @param fileName 文件名称
     * @return 返回文件信息
     */
    public static Object getObject(MinioClient client, String bucketName, String fileName) {
        try {
            GetObjectResponse response = client.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
            Map<String, Object> result = new HashMap<>();
            result.put("bucket", response.object());
            result.put("file", response.object());
            return result;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 预览文件
     * @param client 客户端
     * @param bucketName 桶名称
     * @param fileName 文件名称
     * @return 返回文件路径
     */
    public static String preview(MinioClient client, String bucketName, String fileName){
        // 查看文件地址
        try {
            return client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .method(Method.GET)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("预览文件出错!" + e.getMessage());
        }
    }

    /**
     * 文件下载
     * @param client 客户端
     * @param bucketName 桶名称
     * @param fileName 文件名称
     * @param res response 输出流对象
     */
    public static void download(MinioClient client, String bucketName, String fileName, HttpServletResponse res) {
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(bucketName).object(fileName).build();
        try (GetObjectResponse response = client.getObject(objectArgs)){
            byte[] buf = new byte[1024 * 8];
            int len;
            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {
                while ((len = response.read(buf)) != -1) {
                    os.write(buf, 0, len);
                }
                os.flush();
                byte[] bytes = os.toByteArray();
                res.setCharacterEncoding("utf-8");
                res.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                try (ServletOutputStream stream = res.getOutputStream()) {
                    stream.write(bytes);
                    stream.flush();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("下载文件出错!" + e.getMessage());
        }
    }

    /**
     * 查看文件对象
     * @param client 客户端
     * @param bucketName 桶名称
     * @return 存储bucket内文件对象信息
     */
    public static List<Item> listObjects(MinioClient client, String bucketName) {
        Iterable<Result<Item>> results = client.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
        return StreamSupport.stream(results.spliterator(), false).map(result -> {
            try {
                return result.get();
            } catch (Exception e) {
                throw new RuntimeException("查看文件对象列表出错：" + e.getMessage());
            }
        }).collect(Collectors.toList());
    }

    /**
     * 删除文件
     * @param client 客户端
     * @param bucketName 桶名称
     * @param fileName 文件名
     */
    public static void remove(MinioClient client, String bucketName, String fileName){
        try {
            client.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());
        }catch (Exception e){
            throw new RuntimeException("删除文件出错!" + e.getMessage());
        }
    }
}
