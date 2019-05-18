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
    <link rel="stylesheet" type="text/css" href="/css/formal.css" />
    <link rel="stylesheet" type="text/css" href="/css/bootstrapValidator.min.css" />
</head>
<body>
    <!--主题内容  栅格系统-->
    <div class="row" id="userCenter">
        <div class="col-md-2"></div>
        <div class="colorBorder col-md-6 colDiv">
            <!--时间提示-->
            <div class="colDiv">
                <div id="time" class="title">
                    <span class="font-tip">{{nowTime}}</span>
                </div>
            </div>
            <!--跳转按钮-->
            <div class="colDiv row">
                <div class="col-md-3 colDiv">
                    <button type="button" class="btn btn-default btn-lg" aria-label="Left Align"><span class="glyphicon glyphicon-user" aria-hidden="true"></span>个人信息</button>
                </div>
                <div class="col-md-3 colDiv">
                    <button type="button" class="btn btn-default btn-lg" aria-label="Left Align"><span class="glyphicon glyphicon-comment" aria-hidden="true"></span>最新消息</button>
                </div>
                <div class="col-md-3 colDiv">
                    <button type="button" class="btn btn-default btn-lg" aria-label="Left Align"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>发布信息</button>
                </div>
                <div class="col-md-3 colDiv">
                    <button type="button" class="btn btn-default btn-lg" aria-label="Left Align"><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>设置</button>
                </div>
            </div>
            <!--信息动态-->
            <div class="colDiv">
                <div class="title">
                    <span class="font-tip">最新信息广东药科大学</span>
                </div>
                <div>
                    <div class="row colDiv" v-for="info in infoList">
                        <#--<div class="col-md-2"><img class="img" src="static/img/1.jpg" /></div>-->
                        <div class="col-md-10">
                            <span class="font-tip">  广东药科大学</span>
                            <p><span class="font-tip">{{info.infoTitle}}</span></p>
                            <p><span class="font-tip">{{info.time}}</span></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--侧边栏-->
        <div class="colorBorder1 col-md-3 nopadding">
           <#-- <div class="colDiv content">
                <div class="title">
                    <span class="font-tip">User Group</span>
                </div>
                <div class="left-tip">
                    <ul class="nav nav-pills nav-stacked" v-for='group in userGroup'>
                        <li><a href="#">{{group.groupName}}</a></li>
                    </ul>
                </div>
            </div>-->
            <div class="colDiv content">
                <div class="title">
                    <span class="font-tip">动态{{requestContent[0].groupName}}</span>
                </div>
                <div class="left-tip">
                    <ul class="nav nav-pills nav-stacked" v-for="req in requestContent">
                        <li v-if="req.operation==1"><a href="#">来自{{req.groupName}}的邀请</a></li>
                        <li v-else="req.operation==0"><a href="#">请求加入{{req.groupName}}的申请</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-md-1"></div>
    </div>

<script>
    new Vue( {
        el: '#userCenter',
        data:{

                nowTime: 'test',
                userId:'',
                userGroup:[],
                infoContent:'',
            infoList:[],
                requestContent:''

        },
        methods:{
// 获取当前时间函数
            timeFormate(timeStamp) {
                let year = new Date(timeStamp).getFullYear();
                let month =new Date(timeStamp).getMonth() + 1 < 10? "0" + (new Date(timeStamp).getMonth() + 1): new Date(timeStamp).getMonth() + 1;
                let date =new Date(timeStamp).getDate() < 10? "0" + new Date(timeStamp).getDate(): new Date(timeStamp).getDate();
                let hh =new Date(timeStamp).getHours() < 10? "0" + new Date(timeStamp).getHours(): new Date(timeStamp).getHours();
                let mm =new Date(timeStamp).getMinutes() < 10? "0" + new Date(timeStamp).getMinutes(): new Date(timeStamp).getMinutes();
                // let ss =new Date(timeStamp).getSeconds() < 10? "0" + new Date(timeStamp).getSeconds(): new Date(timeStamp).getSeconds();
                // return year + "年" + month + "月" + date +"日"+" "+hh+":"+mm ;
                this.nowTime = year + "年" + month + "月" + date +"日"+" "+hh+":"+mm;
                // console.log(this.nowTime);
            },
// 定时器函数
            nowTimes(){
                this.timeFormate(new Date());
                setInterval(this.nowTimes,30*1000);
            },
            getNewInfo () {
                const url = "/info/getNewInfo";
                return  axios.post(url,{
                    page:0,
                });
            },
            getUserRequest() {
                const url = "/request/getUserRequest";
                return  axios.post(url,{
                    userId:26,
                    page:0,
                });
            }
        },
        // 挂载完成时
        mounted(){
            this.nowTimes();
            //this.userGroup = this.$store.state.messageStore.groups;
            //this.userId = this.$store.state.messageStore.id;
            this.userId = 26;
            axios.all([this.getNewInfo(),this.getUserRequest()]).then(axios.spread(function (info,req) {
                /*{
              "infoList":[
                {"id":6,"infoBody":"祝福大家在2019年有一个新的开始","infoTitle":"给用户组2的新年祝福","groupId":3,"enclosure":"1.jpg","time":"2019-01-08T07:58:22.000+0000"},
                {"id":5,"infoBody":"用户组ID为3的用户给我听好了！！！","infoTitle":"asdasdasd","groupId":2,"enclosure":"1.jpg","time":"2019-01-07T08:58:04.000+0000"},
                {"id":4,"infoBody":"用户组ID为3的用户给我听好了！！！","infoTitle":"asdasdasd","groupId":2,"enclosure":"1.jpg","time":"2019-01-07T08:55:33.000+0000"},
                {"id":3,"infoBody":"用户组ID为3的用户给我听好了！！！","infoTitle":"asdasdasd","groupId":4,"enclosure":"1.jpg","time":"2019-01-07T08:48:53.000+0000"},
                {"id":2,"infoBody":"用户组ID为3的用户给我听好了！！！","infoTitle":"asdasdasd","groupId":3,"enclosure":"1.jpg","time":"2019-01-07T08:36:57.000+0000"},
                {"id":1,"infoBody":"用户组ID为3的用户给我听好了！！！","infoTitle":"asdasdasd","groupId":2,"enclosure":"1.jpg","time":"2019-01-07T08:31:45.000+0000"}
              ],
              "groupsName":{"2":"group1","3":"group2","4":"group3"},
              "nowPage":0,
              "totalPage":1}*/
                this.infoContent = {
                    "infoList":[
                        {"id":6,"infoBody":"祝福大家在2019年有一个新的开始","infoTitle":"给用户组2的新年祝福","groupId":3,"enclosure":"1.jpg","time":"2019-01-08T07:58:22.000+0000"},
                        {"id":5,"infoBody":"用户组ID为3的用户给我听好了！！！","infoTitle":"asdasdasd","groupId":2,"enclosure":"1.jpg","time":"2019-01-07T08:58:04.000+0000"},
                        {"id":4,"infoBody":"用户组ID为3的用户给我听好了！！！","infoTitle":"asdasdasd","groupId":2,"enclosure":"1.jpg","time":"2019-01-07T08:55:33.000+0000"},
                        {"id":3,"infoBody":"用户组ID为3的用户给我听好了！！！","infoTitle":"asdasdasd","groupId":4,"enclosure":"1.jpg","time":"2019-01-07T08:48:53.000+0000"},
                        {"id":2,"infoBody":"用户组ID为3的用户给我听好了！！！","infoTitle":"asdasdasd","groupId":3,"enclosure":"1.jpg","time":"2019-01-07T08:36:57.000+0000"},
                        {"id":1,"infoBody":"用户组ID为3的用户给我听好了！！！","infoTitle":"asdasdasd","groupId":2,"enclosure":"1.jpg","time":"2019-01-07T08:31:45.000+0000"}
                    ],
                    "groupsName":{"2":"group1","3":"group2","4":"group3"},
                    "nowPage":0,
                    "totalPage":1};
                // const req = {"content":[
                // {"id":2,"userId":26,"groupId":3,"groupName":"group2","operation":0,"result":0,"time":"2019-01-12T04:39:14.000+0000"},
                // {"id":3,"userId":26,"groupId":4,"groupName":"group3","operation":1,"result":0,"time":"2019-01-11T04:39:14.000+0000"},
                // {"id":1,"userId":26,"groupId":2,"groupName":"group1","operation":1,"result":0,"time":"2019-01-10T04:39:14.000+0000"}],
                // "pageable":{"sort":{"sorted":true,"unsorted":false,"empty":false},"offset":0,"pageSize":8,"pageNumber":0,"paged":true,"unpaged":false},"totalPages":1,"totalElements":3,"last":true,"number":0,"size":8,"sort":{"sorted":true,"unsorted":false,"empty":false},"numberOfElements":3,"first":true,"empty":false}
                this.requestContent = req.data.content;
                this.infoList = infoContent.infoList;
                console.log(infoList);
                console.log(requestContent);
            }))
        }
    })
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->


</body>
</html>