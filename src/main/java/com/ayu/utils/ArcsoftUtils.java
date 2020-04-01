package com.ayu.utils;


import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.toolkit.ImageInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

public class ArcsoftUtils {
    private File picture;//传入的照片
    private FaceEngine faceEngine;//引擎
    private int errorCode;//错误代码
    private ActiveFileInfo activeFileInfo;//激活信息
    private EngineConfiguration engineConfiguration;//引擎配置
    private FunctionConfiguration functionConfiguration;//功能配置
    private ImageInfo imageInfo;//图像检测
    private List<FaceInfo> faceInfoList;//人脸信息
    private FunctionConfiguration configuration;//人脸属性检测配置
    FaceFeature faceFeature = new FaceFeature();//原照片特征
    FaceSimilar faceSimilar = new FaceSimilar(); //对比引擎
    public ArcsoftUtils(File picture) {
        this.picture = picture;
        //从官网获取
        String appId = "5GUFsEVpH7CLuMNpMCTsVrqPEyvPWCYvvY2yJbxLyp1c";
        String sdkKey = "EYL1qsVSziLDmaiU1aH9V1qhSpEaUTh5x6ogXsnsH6cY";
        File file = new File(ArcsoftUtils.class.getResource("/").getPath());
        faceEngine = new FaceEngine(file.getAbsolutePath()+"/arcsoft_lib");
        //激活引擎
        errorCode = faceEngine.activeOnline(appId, sdkKey);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("引擎激活失败");
        }
        //激活文件
        activeFileInfo = new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("获取激活文件信息失败");
        }
        //引擎配置
        engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        engineConfiguration.setDetectFaceMaxNum(10);
        engineConfiguration.setDetectFaceScaleVal(16);
        //功能配置
        functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportGender(true);
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);
        //初始化引擎
        errorCode = faceEngine.init(engineConfiguration);

        if (errorCode != ErrorInfo.MOK.getValue()) {
            System.out.println("初始化引擎失败");
        }
        //人脸检测
        imageInfo = getRGBData(picture);
        faceInfoList = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
        //设置活体测试
        errorCode = faceEngine.setLivenessParam(0.5f, 0.7f);
        //人脸属性检测
        configuration = new FunctionConfiguration();
        configuration.setSupportAge(true);
        configuration.setSupportFace3dAngle(true);
        configuration.setSupportGender(true);
        configuration.setSupportLiveness(true);
        errorCode = faceEngine.process(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList, configuration);
        faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);

    }
    //获取性别
    public int getGender(){
        List<GenderInfo> genderInfoList = new ArrayList<GenderInfo>();
        errorCode = faceEngine.getGender(genderInfoList);
        return genderInfoList.get(0).getGender();
    }
    //获取年龄
    public int getAge() {
        List<AgeInfo> ageInfoList = new ArrayList<AgeInfo>();
        errorCode = faceEngine.getAge(ageInfoList);
        return ageInfoList.get(0).getAge();
    }
    //人脸对比
    public double compareWithFile(File file){
        //target要进行匹配的照片
        ArcsoftUtils target = new ArcsoftUtils(file);
        //原照片的特征
        FaceFeature sourceFaceFeature = new FaceFeature();
        sourceFaceFeature.setFeatureData(this.getFaceFeature().getFeatureData());
        //要被对比的特征
        FaceFeature targetFaceFeature = new FaceFeature();
        targetFaceFeature.setFeatureData(target.getFaceFeature().getFeatureData());
        //对比引擎
        FaceSimilar faceSimilar = new FaceSimilar();
        //进行对比
        errorCode = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);

        return faceSimilar.getScore();
    }
    public double compareWithFeature(FaceFeature targetFaceFeature){
        //原照片的特征
        FaceFeature sourceFaceFeature = new FaceFeature();
        sourceFaceFeature.setFeatureData(this.getFaceFeature().getFeatureData());
        //对比引擎
        FaceSimilar faceSimilar = new FaceSimilar();
        //进行对比
        errorCode = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);
        return faceSimilar.getScore();
    }
    public double compareWithFeature(FaceFeature faceFeature1,FaceFeature faceFeature2){
        errorCode =faceEngine.compareFaceFeature(faceFeature1,faceFeature2,faceSimilar);
        return faceSimilar.getScore();
    }
    public double compareWithFeature(byte target[]){
        FaceFeature targetFaceFeature=new FaceFeature(target);
        //进行对比
        errorCode = faceEngine.compareFaceFeature(targetFaceFeature, faceFeature, faceSimilar);
        return faceSimilar.getScore();
    }
    //获取人脸特征
    public FaceFeature getFaceFeature(){
        FaceFeature faceFeature = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
        return faceFeature;
    }
    //销毁引擎
    public void close(){
        faceEngine.unInit();
    }
}
