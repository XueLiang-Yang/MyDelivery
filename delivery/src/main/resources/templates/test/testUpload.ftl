<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="/js/vue.js"></script>
    <script src="/js/jquery-3.2.1.min.js"></script>
    <script src="/js/bootstrap.js"></script>
    <script src="/js/axios.js"></script>
    <script src="/js/bootstrapValidator.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css" />

    <link rel="stylesheet" type="text/css" href="/css/bootstrapValidator.min.css" />
</head>
<body>
<input type="file" class="file">
<button onclick="upload()">提交</button>
<script type="text/javascript">
    function upload() {
        var formData = new FormData() // 声明一个FormData对象
        //var formData = new window.FormData() // vue 中使用 window.FormData(),否则会报 'FormData isn't definded'

        //'userfile'是formData这个对象的键名
        // 'userfile' 这个名字要和后台获取文件的名字一样;
        formData.append('userfile', document.querySelector('input[type=file]').files[0]);
        formData.append('info1', "我是信息1");
        formData.append('info2', "我是信息2");
        var options = {  // 设置axios的参数
            url: '/testUpload',
            data: formData,
            method: 'post',
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }
        this.axios(options).then((res) => {}) // 发送请求
    }
</script>
</body>
</html>