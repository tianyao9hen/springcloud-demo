<template>
    <div class="header">
        <!-- 折叠按钮 -->
        <div class="collapse-btn"><!--@click="collapseChage"-->
            <i class="el-icon-menu"></i>
        </div>
        <div class="logo">SpringCloud demo</div>
        <div class="header-right">
            <div class="header-user-con">
                <!--欢迎访问管理系统，{{username}}！-->
                <!-- 全屏显示 -->
                <!--<div class="btn-fullscreen" @click="handleFullScreen">
                  <el-tooltip effect="dark" :content="fullscreen?`取消全屏`:`全屏`" placement="bottom">
                    <i class="el-icon-rank"></i>
                  </el-tooltip>
                </div>-->
                <!-- 消息中心 -->
                <!--<div class="btn-bell">
                  <el-tooltip effect="dark" :content="message?`有${message}条未读消息`:`消息中心`" placement="bottom">
                    <router-link to="/tabs">
                      <i class="el-icon-bell"></i>
                    </router-link>
                  </el-tooltip>
                  <span class="btn-bell-badge" v-if="message"></span>
                </div>-->
                <!-- 用户头像 -->
                <div class="user-avator">
                    <img src="../../assets/img/touxiang.jpg">
                </div>
                <!-- 用户名下拉菜单 -->
                <el-dropdown class="user-name" trigger="click" @command="handleCommand">
                    <span class="el-dropdown-link">
                        {{username}}
                        <i class="el-icon-caret-bottom"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <!--<router-link to="/admin">
                            <el-dropdown-item>用户信息</el-dropdown-item>
                        </router-link>-->
                        <el-dropdown-item command="upPassword">修改密码</el-dropdown-item>
                        <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </div>

    </div>
</template>

<script>
    import {logout} from 'network/auth'

    export default {
        name: 'Header',
        data(){
            return {
                collapse: false,
                fullscreen: false,
                //username: 'admin',
                message: 2,
            }
        },
        computed: {
            username(){
                let userEntity = JSON.parse(sessionStorage.getItem('userEntity'));
                if(userEntity != null) return userEntity.userName;
                return "";
            }
        },
        watch: {
            username(){
                let userEntity = JSON.parse(sessionStorage.getItem('userEntity'));
                if(userEntity != null) return userEntity.userName;
                return "";
            }
        },
        methods: {
            handleCommand(command){
                if(command == 'logout'){
                    this.$confirm('是否退出登陆?', '退出', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        logout().then(res => {
                            this.$router.replace("/login")
                        })
                    })
                }
            }
        }
    }

</script>

<style scoped>
    .header {
        background-color: #303133;
        position: relative;
        box-sizing: border-box;
        width: 100%;
        height: 50px;
        font-size: 22px;
        color: #fff;
    }
    .collapse-btn {
        float: left;
        padding: 0 15px;
        cursor: pointer;
        line-height: 50px;
        margin: 0 5px;
    }
    .header .logo {
        float: left;
        width: 300px;
        font-size: 18px;
        line-height: 50px;
        padding: 0 15px;
    }
    .header-right {
        float: right;
        padding-right: 50px;
    }
    .header-user-con {
        display: flex;
        height: 50px;
        align-items: center;
    }
    .btn-fullscreen {
        transform: rotate(45deg);
        margin-right: 5px;
        font-size: 24px;
    }
    .btn-bell,
    .btn-fullscreen {
        position: relative;
        width: 30px;
        height: 30px;
        text-align: center;
        border-radius: 15px;
        cursor: pointer;
    }
    .btn-bell-badge {
        position: absolute;
        right: 0;
        top: -2px;
        width: 8px;
        height: 8px;
        border-radius: 4px;
        background: #f56c6c;
        color: #fff;
    }
    .btn-bell .el-icon-bell {
        color: #fff;
    }
    .user-name {
        margin-left: 10px;
    }
    .user-avator {
        margin-left: 20px;
    }
    .user-avator img {
        display: block;
        width: 30px;
        height: 30px;
        border-radius: 50%;
        border: solid 2px #c1c1c1;
    }
    .el-dropdown-link {
        color: #fff;
        cursor: pointer;
    }
    .el-dropdown-menu__item {
        text-align: center;
    }
    .collapse-btn:hover{
        background-color: #616161;
    }
</style>