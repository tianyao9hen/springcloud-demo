<template>
    <div class="wrapper">
        <div v-if="isLogin">
            <v-header/>
        </div>
    </div>
</template>

<script>
    import vHeader from './Header'
    import {getCookie,setCookie,removeCookie} from 'common/cookieUtils'
    import {login} from 'network/auth'

    export default {
        name: 'Home',
        data(){
            return {
                isLogin: false,
                userEntity: null
            }
        },
        components: {
            vHeader
        },
        created(){
            //判断是否登陆
            this.userEntity = JSON.parse(sessionStorage.getItem('userEntity'));
            if(this.userEntity == null){
                let loginName = getCookie("loginName");
                let loginPassword = getCookie("loginPassword");
                if(loginName && loginPassword){
                    login({
                        loginName,
                        loginPassword
                    }).then(res => {
                        this.isLogin = true;
                    }).catch(error => {
                        this.$router.replace('/login')
                    })
                }else{
                    this.$router.replace('/login')
                }
            }else{
                this.isLogin = true;
            }
        }
    }

</script>

<style scoped>

</style>