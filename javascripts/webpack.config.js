var path = require('path');
 
module.exports = {
  entry: './scramble_game.js',
  output: {
	path: '../public/javascripts',
    filename: "bundle.js"
  },
  module: {
    loaders: [
      {
        test: /.jsx?$/,
        loader: 'babel-loader',
        exclude: /node_modules/,
        query: {
          presets: ['es2015', 'react']
        }
      }
    ]
  },
};