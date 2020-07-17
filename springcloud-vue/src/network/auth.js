/**
 * Created by admin on 2020/7/16.
 */
import {request} from './request'
import {setCookie,removeCookie} from 'common/cookieUtils'
import Qs from 'qs'

const preUrl = '/auth';

//登陆
export function login(param){
    return new Promise((resolve,reject) => {
        request({
            url: preUrl + '/login',
            method: 'post',
            data: Qs.stringify(param),
        }).then(res => {
            let result = res.data;
            if(result.success){
                setCookie('loginName',param.loginName,172800);
                setCookie('loginPassword',param.loginPassword,172800);
                sessionStorage.setItem('authorization',result.result.token);
                sessionStorage.setItem('userEntity',JSON.stringify(result.result));
                resolve(result.result);
            }else{
                removeCookie('loginName');
                removeCookie('loginPassword');
                sessionStorage.removeItem('authorization');
                sessionStorage.removeItem('userEntity');
                reject(result.error);
            }
        })
    })
}