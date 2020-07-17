/**
 * Created by admin on 2020/7/16.
 */
import axios from 'axios'

export function request(config) {
    let tokenHeader = {}
    if(sessionStorage.getItem("authorization")){
        tokenHeader = {
            'token': sessionStorage.getItem('authorization'),
        }
    }
    const instance = axios.create({
        baseURL: "http://localhost",
        timeout: 5000,
        headers: tokenHeader
    })
    return instance(config)
}