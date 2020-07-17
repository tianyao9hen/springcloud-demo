<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">SpringCloud框架demo</div>
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="0px" class="ms-content"
                     v-loading="loginLoading"
                     element-loading-text="登陆中，请稍后！"
                     element-loading-spinner="el-icon-loading">
                <el-form-item prop="loginName">
                    <el-input v-model="ruleForm.loginName" placeholder="请输入用户名">
                        <el-button slot="prepend" class="el-icon-user-solid"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="loginPassword">
                    <el-input type="password" placeholder="请输入密码" v-model="ruleForm.loginPassword">
                        <el-button slot="prepend" class="el-icon-lock"></el-button>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="login()">登录</el-button>
                </div>
            </el-form>
        </div>
    </div>
</template>

<script>
    import {login} from 'network/auth'
    import {setCookie,removeCookie} from 'common/cookieUtils'

    export default {
        name: 'Login.vue',
        data(){
            return {
                loginLoading: false,
                ruleForm: {
                    loginName: '',
                    loginPassword: ''
                },
                rules: {
                    loginName: [
                        { required: true, message: '请输入用户名', trigger: 'blur' }
                    ],
                    loginPassword: [
                        { required: true, message: '请输入密码', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            login(){
                this.$refs.ruleForm.validate(valid => {
                    if (valid) {
                        this.loginLoading = true;
                        login(this.ruleForm).then(res => {
                            //登陆成功
                            this.loginLoading = false;
                            this.$router.replace("/")
                        }).catch(error => {
                            this.loginLoading = false;
                            //登陆失败
                            this.$message({
                                showClose: true,
                                message: error,
                                type: 'error',
                                center: true
                            });
                        })
                    } else {
                        //验证失败
                        return false;
                    }
                })
            },
        }
    }

</script>

<style scoped>
    .login-wrap{
        width:100vw;
        height:100vh;
    }
    .ms-title{
        width:100%;
        line-height: 50px;
        text-align: center;
        font-size:20px;
        color: #000;
        border-bottom: 1px solid #ddd;
    }
    .ms-login{
        position: absolute;
        left:50%;
        top:50%;
        width:350px;
        margin:-190px 0 0 -175px;
        border-radius: 5px;
        background: rgba(255,255,255, 1);
        overflow: hidden;
    }
    .ms-content{
        padding: 30px 30px;
    }
    .login-btn{
        text-align: center;
    }
    .login-btn button{
        width:100%;
        height:36px;
        margin-bottom: 10px;
    }
    .login-tips{
        font-size:12px;
        line-heigh:#000;
    }
</style>