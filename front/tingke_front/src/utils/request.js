import axios from 'axios';
import cookie from 'js-cookie'

const service = axios.create({
    // process.env.NODE_ENV === 'development' 来判断是否开发环境
    // easy-mock服务挂了，暂时不使用了
    baseURL: 'http://localhost:9999',
    timeout: 5000
});

// http request 拦截器
service.interceptors.request.use(
  config => {
    //判断cookie是否有token
    if (cookie.get('user_token')) {
      //获取用户信息，并放置在头信息
      config.headers['token'] = cookie.get('user_token');
    }
    return config
  },
  err => {
    return Promise.reject(err);
  }),

service.interceptors.response.use(
    response => {
        if (response.status === 200) {
            return response.data;
        } else {
            Promise.reject();
        }
    },
    error => {
        console.log(error);
        return Promise.reject();
    }
);

export default service;
