module.exports = (on) => {
    const pnp = require('.pnp.cjs');
    
    // I couldn't find this documented, but this appears to monkey-patch require and do all the other necessary things to get PnP working
    pnp.setup();

    // This is where we'll be resolving packages from. In this case, it is a workspace package we have named, but using a relative path here should work as well to resolve from a specific folder.
    const targetModule = 'hanglog-service';

    // We need to patch the webpack config that cypress uses (which is v4 which needs a plugin for PnP support)
    const webpackPreprocessor = require(pnp.resolveToUnqualified('@cypress/webpack-preprocessor', targetModule));
    const PnpWebpackPlugin = require(pnp.resolveToUnqualified('pnp-webpack-plugin', targetModule));

    const options = {
        webpackOptions: {
            resolve: {
                plugins: [
                    PnpWebpackPlugin
                ]
            },
            resolveLoader: {
                plugins: [
                    PnpWebpackPlugin.moduleLoader(module)
                ]
            }
        },
        watchOptions: {}
    };

    on('file:preprocessor', webpackPreprocessor(options));
};