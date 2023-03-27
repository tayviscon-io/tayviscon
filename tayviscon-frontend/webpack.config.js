const path = require('path');
const {CleanWebpackPlugin} = require('clean-webpack-plugin');
const webpack = require('webpack');
const TerserPlugin = require('terser-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const OptimizeCSSAssetsPlugin = require('optimize-css-assets-webpack-plugin');
const CopyPlugin = require('copy-webpack-plugin');

module.exports = {
    entry: {
        main: './src/app/main.js',
        guide: './src/app/guide.js',
        run_prettify: './src/app/run_prettify.js',
    },

    output: {
        path: path.resolve(__dirname, 'build', 'dist'),
        filename: 'js/[name].js'
    },
    plugins: [
        new CleanWebpackPlugin(),
        new MiniCssExtractPlugin({
            filename: 'css/[name].css',
            chunkFilename: '[id].css'
        }),
        new CopyPlugin([
            {from: './src/images', to: 'images'}
        ]),
        new webpack.ProvidePlugin({
            $: 'jquery',
            jQuery: 'jquery',
            'window.jQuery': 'jquery',
            'window.$': 'jquery'
        }),

    ],
    module: {
        rules: [

            {
                test: /\.scss$/,
                use: [
                    {
                        loader: MiniCssExtractPlugin.loader,
                        options: {publicPath: '../'},
                    },
                    'css-loader',
                    'sass-loader',
                ],
            },
            {
                test: /\.css$/,
                use: [
                    {
                        loader: MiniCssExtractPlugin.loader,
                        options: {publicPath: '../'},
                    },
                    'css-loader',
                ]
            },
            {
                test: /.*\/webfonts\/.*$/,
                loader: 'file-loader',
                options: {
                    name: 'css/[name].[ext]',
                },
            },

            {
                test: /.*\/fonts\/.*/,
                loader: 'file-loader',
                options: {
                    name: 'fonts/[name].[ext]',
                },
            },
            {
                test: /^images/,
                loader: 'file-loader',
                options: {
                    name: '[path][name].[ext]',
                },
            }
        ]
    },
    optimization: {
        minimize: true,
        runtimeChunk: false,
        minimizer: [
            new OptimizeCSSAssetsPlugin({}),
            new TerserPlugin()
        ]
    }
};