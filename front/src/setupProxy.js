const { createProxyMiddleware } = require('http-proxy-middleware');

console.log("✅ setupProxy.js 로드됨");

module.exports = function (app) {
app.use(
    '/api',
    createProxyMiddleware({
    target: 'http://59.24.237.51:8080',
    pathRewrite: {
        '^/api': '',
    },
    changeOrigin: true
    }),
);
};