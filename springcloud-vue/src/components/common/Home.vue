<template>
    <div class="wrapper">
        <template v-if="isLogin">
            <v-header/>
            <v-sidebar/>
        </template>
    </div>
</template>

<script>
    import vHeader from './Header'
    import vSidebar from './Sidebar'

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
            vHeader,
            vSidebar
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