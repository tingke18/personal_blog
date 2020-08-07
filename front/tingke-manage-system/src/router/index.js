import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [

        {
            path: '/',
            redirect: '/dashboard'
        },
        {
            path: '/',
            component: () => import(/* webpackChunkName: "home" */ '../components/common/Home.vue'),
            meta: { title: '自述文件' },
            children: [
                {
                    path: "/article",
                    redirect:'/article_list',
                },
                {
                    path: "/article_list",
                    component: () => import(/* webpackChunkName: "login" */ '../components/page/article/article_list.vue'),
                    meta: { title: '文章列表' },
                },
                {
                    path: "/article_add",
                    component: () => import(/* webpackChunkName: "login" */ '../components/page/article/article_add.vue'),
                    meta: { title: '文章添加' }
                },
                {
                    path: "/article_browse",
                    component: () => import(/* webpackChunkName: "login" */ '../components/page/article/article_browse.vue'),
                    meta: { title: '文章预览' },
                },
                {
                    path: "/presonal_information",
                    component: () => import(/* webpackChunkName: "login" */ '../components/page/information/presonal_information.vue'),
                    meta: { title: '个人信息' },
                },

                {
                    path: "/diary_list",
                    component: () => import(/* webpackChunkName: "login" */ '../components/page/diary/diary_list'),
                    meta: { title: '随笔列表' },
                },
                {
                    path: "/diary_add",
                    component: () => import(/* webpackChunkName: "login" */ '../components/page/diary/diary_add'),
                    meta: { title: '随笔添加' }
                },
                {
                    path: "/diary_browse",
                    component: () => import(/* webpackChunkName: "login" */ '../components/page/diary/diary_browse'),
                    meta: { title: '随笔预览' },
                },

                {
                    path: "/link_list",
                    component: () => import(/* webpackChunkName: "login" */ '../components/page/link/link_list'),
                    meta: { title: '链接列表' },
                },
                {
                    path: "/link_add",
                    component: () => import(/* webpackChunkName: "login" */ '../components/page/link/link_add'),
                    meta: { title: '链接添加' }
                },

                {
                    path: "/book_list",
                    component: () => import(/* webpackChunkName: "login" */ '../components/page/book/book_list'),
                    meta: { title: '图书列表' },
                },
                {
                    path: "/book_add",
                    component: () => import(/* webpackChunkName: "login" */ '../components/page/book/book_add'),
                    meta: { title: '图书添加' }
                },

                {
                    path: "/comment_list",
                    component: () => import(/* webpackChunkName: "login" */ '../components/page/comment/comment_list'),
                    meta: { title: '评论列表' },
                },
                {
                    path: "/comment_add",
                    component: () => import(/* webpackChunkName: "login" */ '../components/page/comment/comment_add'),
                    meta: { title: '评论添加' }
                },
                {
                    path: '/dashboard',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/Dashboard.vue'),
                    meta: { title: '系统首页' }
                },
                {
                    path: '/tabs',
                    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/Tabs.vue'),
                    meta: { title: '消息处理' }
                },
                {
                    // 权限页面
                    path: '/permission',
                    component: () => import(/* webpackChunkName: "permission" */ '../components/page/Permission.vue'),
                    meta: { title: '权限测试', permission: true }
                },
                {
                    path: '/404',
                    component: () => import(/* webpackChunkName: "404" */ '../components/page/404.vue'),
                    meta: { title: '404' }
                },
                {
                    path: '/403',
                    component: () => import(/* webpackChunkName: "403" */ '../components/page/403.vue'),
                    meta: { title: '403' }
                },
            ]
        },
        {
            path: '/login',
            component: () => import(/* webpackChunkName: "login" */ '../components/page/Login.vue'),
            meta: { title: '登录' }
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
});
