src = "https://cdn.bootcss.com/jquery/3.4.0/jquery.min.js"
let mediaStreamTrack = null; // 视频对象(全局)
let video;

//注册打开摄像头
function openRegistMedia() {
    let constraints = {
        video: {width: 500, height: 500},
        audio: false
    };
    //获得video摄像头
    video = document.getElementById('video');
    let promise = navigator.mediaDevices.getUserMedia(constraints);
    promise.then((mediaStream) => {
        // mediaStreamTrack = typeof mediaStream.stop === 'function' ? mediaStream : mediaStream.getTracks()[1];
        mediaStreamTrack = mediaStream.getVideoTracks()
        video.srcObject = mediaStream;
    video.play();
})
    ;
}

// 注册拍照方法
function takeRegistPhoto() {
    //获得Canvas对象
    let video = document.getElementById('video');
    let canvas = document.getElementById('canvas');
    let ctx = canvas.getContext('2d');
    ctx.drawImage(video, 0, 0, 200, 200);


    // toDataURL  ---  可传入'image/png'---默认, 'image/jpeg'
    let img = document.getElementById('canvas').toDataURL();
    // 这里的img就是得到的图片
    console.log('img-----', img);
    //document.getElementById('preview').src = img;
    var docObj = document.getElementById("registerFile01");
    docObj.files[0] = img;
    document.getElementById('takeText').value = img;
    document.getElementById('takeSubmit').click();
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

function registerCheck() {
    var objExp = /[\u4E00-\u9FA5]{2,}/;
    //验证姓名
    if (objExp.test(document.getElementById("name1").value) == false) {
        alert("您输入的姓名有误！");
        return;
    } else if (document.getElementById("idcard1").value.length != 18) {
        alert("您输入的身份证有误，请输入18位身份证");
        return;
    } else if (Number(document.getElementById("idcard1").value.substring(6, 10) > new Date().getFullYear() + 1)) {
        alert("您不能在未来出生")
        return;
    } else if (Number(document.getElementById("idcard1").value.substring(6, 10)) < 1900) {
        alert("您的年龄已经超过了120岁")
        return;
    } else if (document.getElementById("registerFile01").value == "") {
        alert("请选择文件")
        return;
    }
    document.getElementById("tijiao").click();
}

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