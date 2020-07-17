/**
 * Created by admin on 2020/7/16.
 */
export function setCookie(key,value,seconds){
    let exdate = new Date();
    exdate.setTime(exdate.getTime()+seconds * 1000);
    window.document.cookie = key + "=" + escape(value) + ((seconds == null) ? "" : ";expires=" + exdate.toUTCString())
}

export function removeCookie(key) {
    setCookie(key,'',-1);
}

export function getCookie(key){
    let cookieArr = window.document.cookie.split(';');
    for(let i = 0; i < cookieArr.length; i++) {
        let arr = cookieArr[i].split('=');
        let cookieKey = arr[0].replace(/^\s*|\s*$/g,"")
        if(cookieKey === key) {
            return arr[1];
        }
    }
    return false;
}