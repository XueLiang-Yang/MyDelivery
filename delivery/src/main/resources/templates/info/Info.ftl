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
    <script src="/js/fileinput.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="/css/formal.css" />
    <link rel="stylesheet" type="text/css" href="/css/fileinput.css" />
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">即时高校信息发布系统</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

            <form class="navbar-form navbar-left">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Search">
                </div>
                <button type="submit" class="btn btn-default">Submit</button>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">我的空间</span></a>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">个人中心</a>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">用户组 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">我的用户组</a></li>
                        <li><a href="#">新建用户组</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">信息 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">最新信息</a></li>
                        <li><a href="#">动态</a></li>
                        <li><a href="#">发布信息</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">姓名！！！ <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">设置</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">退出登录</a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<div class="info-content">
    <div id="infoForm">
        <h3><span class="glyphicon glyphicon-tag" aria-hidden="true"></span>信息标题</h3>
        <div class="input-group">
            <span class="input-group-addon" id="sizing-addon2">Title</span>
            <input type="text" class="form-control" id="infoTitle" v-model="infoTitle" placeholder="信息标题" aria-describedby="sizing-addon2">
        </div>
        <div class="input-group">
            <span class="input-group-addon">用户组</span>
            <input type="text" class="form-control" data-toggle="modal" data-target="#myModal" id="usergroup" v-model="groupName" readonly placeholder="请选择用户组" aria-describedby="sizing-addon2">
        </div>
        <!-- 模态框（Modal） -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">您的用户组</h4>
                    </div>
                    <div class="modal-body">
                        <ul class="nav nav-pills nav-stacked">
                            <li><a href="javascript:void(0)" @click="selectUserGroup($event)" data="1">用户组1</a></li>
                            <li><a href="javascript:void(0)" @click="selectUserGroup($event)" data="2">用户组2</a></li>
                            <li><a href="javascript:void(0)" @click="selectUserGroup($event)" data="3">用户组3</a></li>
                        </ul>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary" data-dismiss="modal">确认提交</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal -->
        </div>
        <div>
            <h3><span class="glyphicon glyphicon-list" aria-hidden="true"></span>信息内容</h3>
            <textarea class="textArea" v-model="infoContent" id="infoContent"></textarea>
        </div>
        <div class="colDiv">
            <!--  <input id="lefile" type="file" @change="tirggerFile($event)" accept="application/x-zip-compressed,application/x-rar-compressed" style="display: none">-->

            <div class="input-append">
                <label class="control-label" for="testfile">上传文件</label>
                <input type="file" @change="selectFile($event)" id="testfile" name="uploadFile" multiple />
            </div>
        </div>
        <div class="colDiv centerButton">
            <button class="btn btn-info" @click="deliver($event)">提交</button>
        </div>
    </div>

</div>



<script type="text/javascript">

    new Vue({
        el:'#infoForm',
        data:{
            infoTitle:"",
            infoContent:"",
            fileName:"",
            group:"",
            groupName:""
        },
        mounted:function(){
            $('#testfile').fileinput({
                language: 'zh',
                uploadUrl: '/info/toUpload',
                showCaption: true,
                showUpload: true,
                showRemove: true,
                showClose: true,
                maxFileCount:1,
                layoutTemplates:{
                    actionDelete: ''
                },
                browseClass: 'btn btn-primary'
            });
        },
        methods:{
            deliver:function (event) {
                var formData = new FormData() // 声明一个FormData对象
                //var formData = new window.FormData() // vue 中使用 window.FormData(),否则会报 'FormData isn't definded'

                formData.append('fileName', this.fileName);
                formData.append('infoTitle', this.infoTitle);
                formData.append('infoContent', this.infoContent);
                formData.append('groupId', this.group);


                var options = {  // 设置axios的参数
                    url: '/info/deliveryNewInfo',
                    data: formData,
                    method: 'post',
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }
                // 发送请求
                axios(options).then(function (res) {
                    if(res.data == "success"){
                        alert("信息发布成功");
                    }else {
                        alert("信息发布失败，请稍后重试！！！");
                    }
                })
            },
            selectFile:function(event){
                this.fileName = event.target.files[0].name;
            },
            selectUserGroup:function (obj) {
                this.group = $(obj.srcElement).attr('data');
                this.groupName = obj.srcElement.innerHTML;
            }
        }
    })
</script>
</body>
</html>