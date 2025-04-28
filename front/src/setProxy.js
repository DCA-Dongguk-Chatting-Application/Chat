const {createProxyMiddleware} = require('http-proxy-middleware');

module.exports = function(app){
    app.use(
        '/api',
        createProxyMiddleWare({
            target : 'http://59.24.237.51:8080',
            changeOrigin : true,
        })
    );
};