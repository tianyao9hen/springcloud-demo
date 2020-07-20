<template>
    <div class="sidebar">
        <el-menu class="sidebar-el-menu"
                 background-color="#525354"
                 text-color="#fff"
                 active-text-color="#ffd04b"
                 unique-opened
                 router>
            <template v-for="item in menuData">
                <template v-if="item.childs">
                    <el-submenu :index="item.seq" :key="item.seq">
                        <template slot="title">
                            <i :class="item.icon"></i>
                            <span slot="title">{{item.name}}</span>
                        </template>
                        <template v-for="child in item.childs">
                            <el-menu-item :index="child.href" :key="child.href">
                                <span slot="title">{{child.name}}</span>
                            </el-menu-item>
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.href" :key="item.href">
                        <i :class="item.icon"></i>
                        <span slot="title">{{item.name}}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
    export default {
        data(){
            return {
                menuData: [
                    { "icon": "el-icon-lx-home", "href": "/dashboard", "name": "系统首页", "id": "800201904004", "label": "" },
                    { "icon": "el-icon-lx-settings", "name": "系统管理", "id": "802019091604450001", "label": "",
                        "childs":
                            [
                                { "name":"组织机构", "href":"/dept", "seq":"2", "isShow":"Y" },
                                { "name":"用户组", "href":"/userGroup", "seq":"1", "isShow":"Y" },
                                { "name":"用户管理", "href":"/user", "seq":"5", "isShow":"Y" },
                                { "name":"权限管理", "href":"/perm", "seq":"5", "isShow":"Y" },
                                { "name":"字典管理", "href":"/dict", "seq":"6", "isShow":"Y" },
                                { "name":"系统日志", "href":"/systemLog", "seq":"6", "isShow":"Y" },
                                { "name":"参数设置", "href":"/params", "seq":"6", "isShow":"Y" },
                                { "name":"打印设置", "href":"/printSettings", "seq":"6", "isShow":"Y" },
                                { "name":"运行状态", "href":"/runStatus", "seq":"6", "isShow":"Y" },
                                { "name":"数据库备份", "href":"/databaseBackup", "seq":"6", "isShow":"Y" },
                                { "name":"信息公告", "href":"/bulletin", "seq":"6", "isShow":"Y" }
                            ],
                        "seq":"1"
                    },
                    { "icon": "el-icon-lx-settings", "name": "系统管理", "id": "802019091604450001", "label": "",
                        "childs":
                            [
                                { "name":"组织机构", "href":"/dept", "seq":"2", "isShow":"Y" },
                                { "name":"用户组", "href":"/userGroup", "seq":"1", "isShow":"Y" },
                                { "name":"用户管理", "href":"/user", "seq":"5", "isShow":"Y" },
                                { "name":"权限管理", "href":"/perm", "seq":"5", "isShow":"Y" },
                                { "name":"字典管理", "href":"/dict", "seq":"6", "isShow":"Y" },
                                { "name":"系统日志", "href":"/systemLog", "seq":"6", "isShow":"Y" },
                                { "name":"参数设置", "href":"/params", "seq":"6", "isShow":"Y" },
                                { "name":"打印设置", "href":"/printSettings", "seq":"6", "isShow":"Y" },
                                { "name":"运行状态", "href":"/runStatus", "seq":"6", "isShow":"Y" },
                                { "name":"数据库备份", "href":"/databaseBackup", "seq":"6", "isShow":"Y" },
                                { "name":"信息公告", "href":"/bulletin", "seq":"6", "isShow":"Y" }
                            ],
                        "seq":"2"
                    },
                ]
            }
        }
    }

</script>

<style scoped>
    .sidebar {
        display: block;
        left: 0;
        top: 60px;
        bottom: 0;
        overflow-y: scroll;
        width: auto;
    }
    .sidebar::-webkit-scrollbar {
        width: 0;
    }
    .sidebar-el-menu {
        width: 240px;
    }
</style>