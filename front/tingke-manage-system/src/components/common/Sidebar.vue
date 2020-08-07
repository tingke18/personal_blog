<template>
    <div class="sidebar">
        <el-menu
            class="sidebar-el-menu"
            :default-active="onRoutes"
            :collapse="collapse"
            background-color="#324157"
            text-color="#bfcbd9"
            active-text-color="#20a0ff"
            unique-opened
            router
        >
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-submenu :index="item.index" :key="item.index">
                        <template slot="title">
                            <i :class="item.icon"></i>
                            <span slot="title">{{ item.title }}</span>
                        </template>
                        <template v-for="subItem in item.subs">
                            <el-submenu
                                v-if="subItem.subs"
                                :index="subItem.index"
                                :key="subItem.index"
                            >
                                <template slot="title">{{ subItem.title }}</template>
                                <el-menu-item
                                    v-for="(threeItem,i) in subItem.subs"
                                    :key="i"
                                    :index="threeItem.index"
                                >{{ threeItem.title }}</el-menu-item>
                            </el-submenu>
                            <el-menu-item
                                v-else
                                :index="subItem.index"
                                :key="subItem.index"
                            >{{ subItem.title }}</el-menu-item>
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i>
                        <span slot="title">{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
import bus from '../common/bus';
export default {
    data() {
        return {
            collapse: false,
            items: [
                {
                    icon: 'el-icon-lx-home',
                    index: 'dashboard',
                    title: '系统首页'
                },
                {
                    icon: 'el-icon-lx-read',
                    index: 'article',
                    title: '文章',
                    subs: [
                        {
                            index: 'article_list',
                            title: '文章列表'
                        },
                        {
                            index: 'article_add',
                            title: '文章添加'
                        }
                    ]
                },
                {
                    icon: 'el-icon-lx-link',
                    index: 'link',
                    title: '快捷链接',
                    subs: [
                        {
                            index: 'link_list',
                            title: '链接列表'
                        },
                        {
                            index: 'link_add',
                            title: '链接添加'
                        }

                    ]
                },

                {
                    icon: 'el-icon-lx-forward',
                    index: 'book',
                    title: '推荐图书',
                    subs: [
                        {
                            index: 'book_list',
                            title: '图书列表'
                        },
                        {
                            index: 'book_add',
                            title: '图书添加'
                        }

                    ]
                },

                {
                    icon: 'el-icon-lx-text',
                    index: 'diary',
                    title: '随笔',
                    subs: [
                        {
                            index: 'diary_list',
                            title: '随笔列表'
                        },
                        {
                            index: 'diary_add',
                            title: '随笔添加'
                        }
                    ]
                },
                {
                    icon: 'el-icon-lx-cascades',
                    index: 'presonal_information',
                    title: '个人信息'
                },
                {
                    icon: 'el-icon-lx-copy',
                    index: 'tabs',
                    title: '消息处理'
                }
            ]
        };
    },
    computed: {
        onRoutes() {
            return this.$route.path.replace('/', '');
        }
    },
    created() {
        // 通过 Event Bus 进行组件间通信，来折叠侧边栏
        bus.$on('collapse', msg => {
            this.collapse = msg;
            bus.$emit('collapse-content', msg);
        });
    }
};
</script>

<style scoped>
.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-y: scroll;
}
.sidebar::-webkit-scrollbar {
    width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
    width: 250px;
}
.sidebar > ul {
    height: 100%;
}
</style>
