/**
 * Created by admin on 2020/7/16.
 */
import {request} from './request'
import Qs from 'qs'

const preUrl = '/auth';

export function login(param){
    return request({
        url: preUrl + '/login',
        method: 'post',
        data: Qs.stringify(param),
    })
}
