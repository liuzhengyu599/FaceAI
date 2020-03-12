
var url2="\"http://code.jquery.com/jquery-2.1.4.min.js\"";

document.write("<script language=javascript src="+url2+"></script>");

let mediaStreamTrack = null; // 视频对象(全局)
let video;
let img=null;



//注册打开摄像头
function openRegistMedia() {
    if(check()!=true)
        return;
    let constraints = {
        video: {width: 500, height: 500},
        audio: false
    };
    //获得video摄像头
    video = document.getElementById('video');
    let promise = navigator.mediaDevices.getUserMedia(constraints);
    promise.then((mediaStream) => {
        mediaStreamTrack = mediaStream.getVideoTracks()
        video.srcObject = mediaStream;
        video.play();
    });
    setInterval(takeRegistPhoto,500);
}

// 注册拍照方法
function takeRegistPhoto() {
    //获得Canvas对象
    let video = document.getElementById('video');
    let canvas = document.getElementById('canvas');
    let ctx = canvas.getContext('2d');
    ctx.drawImage(video, 0, 0, 200, 200);

    // toDataURL  ---  可传入'image/png'---默认, 'image/jpeg'
    img = document.getElementById('canvas').toDataURL();
    // 这里的img就是得到的图片
    console.log('img-----', img);
    //preview是预览页面
    //document.getElementById('preview').src = img;
    document.getElementById('takeText').value = img;
    $('#video').faceDetection({
        data: {img},
        complete: function (faces) {
            if (faces.length != 0&&faces[0].confidence>7) {
               registerCheck();
            }
        }
    });

}

// 注册关闭摄像头
function closeMedia() {
    let stream = document.getElementById('video').srcObject;
    let tracks = stream.getTracks();

    tracks.forEach(function (track) {
        track.stop();
    });

    document.getElementById('video').srcObject = null;
}

//注册验证
function registerCheck() {
    if(check()==true&&((img!=null)||document.getElementById("registerFile01").value != "")){
        document.getElementById("tijiao").click();
    }else{
        alert("是否忘记选择文件？")
    }
}

//上传图片预览
function setImagePreview() {
    var docObj = document.getElementById("registerFile01");
    var imgObjPreview = document.getElementById("preview");
    if (docObj.files && docObj.files[0]) {
        //火狐下，直接设img属性
        imgObjPreview.style.display = 'block';
        imgObjPreview.style.width = '200px';
        imgObjPreview.style.height = '200px';
        imgObjPreview.hidden = "";
        //imgObjPreview.src = docObj.files[0].getAsDataURL();
        //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
        imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
    } else {
        //IE下，使用滤镜
        docObj.select();
        var imgSrc = document.selection.createRange().text;
        var localImagId = document.getElementById("localImag"); //必须设置初始大小
        localImagId.style.width = "150px";
        localImagId.style.height = "180px"; //图片异常的捕捉，防止用户修改后缀来伪造图片
        try {
            localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
            localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
        } catch (e) {
            alert("您上传的图片格式不正确，请重新选择!");
            return false;
        }
        imgObjPreview.style.display = 'none';
        document.selection.empty();
    }
    return true;
}

function check() {
    var objExp = /[\u4E00-\u9FA5]{2,}/;
    //验证姓名
    if (objExp.test(document.getElementById("name1").value) == false) {
        alert("您输入的姓名有误！");
        return false;
    } else if (document.getElementById("idcard1").value.length != 18) {
        alert("您输入的身份证有误，请输入18位身份证");
        return false;
    } else if (Number(document.getElementById("idcard1").value.substring(6, 10) > new Date().getFullYear() + 1)) {
        alert("您不能在未来出生")
        return false;
    } else if (Number(document.getElementById("idcard1").value.substring(6, 10)) < 1900) {
        alert("您的年龄已经超过了120岁")
        return false;
    }
    return true;
}



let loginMediaStreamTrack = null; // 视频对象(全局)
let loginVideo;
let loginImg=null;

//登录打开摄像头
function openLoginMedia() {
    let constraints = {
        video: {width: 500, height: 500},
        audio: false
    };
    //获得video摄像头
    loginVideo = document.getElementById('loginVideo');
    let promise = navigator.mediaDevices.getUserMedia(constraints);
    promise.then((mediaStream) => {
        loginMediaStreamTrack = mediaStream.getVideoTracks()
        loginVideo.srcObject = mediaStream;
        loginVideo.play();
    });
    setInterval(takeLoginPhoto,10);
}

// 登录拍照方法
function takeLoginPhoto() {
    //获得Canvas对象
    let loginVideo = document.getElementById('loginVideo');
    let loginCanvas = document.getElementById('loginCanvas');
    let ctx = loginCanvas.getContext('2d');
    ctx.drawImage(loginVideo, 0, 0, 200, 200);

    // toDataURL  ---  可传入'image/png'---默认, 'image/jpeg'
    loginImg = document.getElementById('loginCanvas').toDataURL();
    // 这里的img就是得到的图片
    console.log('img-----', loginImg);


    $('#loginVideo').faceDetection({
        //data: {img},
        complete: function (faces) {
            if (faces.length != 0&&faces[0].confidence>7) {
                document.getElementById('loginText').value = loginImg;
                document.getElementById('loginSubmit').click();
            }
        }
    });
}