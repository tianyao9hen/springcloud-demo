/**
 * Created by admin on 2020/7/16.
 */
module.exports = {
    configureWebpack: {
        resolve: {
            alias: {
                'assets': '@/assets',
                'common': '@/common',
                'components': '@/components',
                'network': '@/network',
                'router': '@/router',
                'store': '@/store',
                'views': '@/views',
            }
        }
    }
}