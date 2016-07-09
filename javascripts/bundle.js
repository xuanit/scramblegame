/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};

/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {

/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;

/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			exports: {},
/******/ 			id: moduleId,
/******/ 			loaded: false
/******/ 		};

/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);

/******/ 		// Flag the module as loaded
/******/ 		module.loaded = true;

/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}


/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;

/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;

/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";

/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ function(module, exports) {

	"use strict";

	var CharacterList = React.createClass({
	    displayName: "CharacterList",

	    render: function render() {
	        return React.createElement(
	            "div",
	            null,
	            "a b c d"
	        );
	    }
	});

	var WordList = React.createClass({
	    displayName: "WordList",

	    render: function render() {
	        return React.createElement(
	            "div",
	            null,
	            React.createElement(
	                "span",
	                null,
	                "a"
	            ),
	            React.createElement(
	                "span",
	                null,
	                "b"
	            )
	        );
	    }
	});

	var GuessingWord = React.createClass({
	    displayName: "GuessingWord",

	    render: function render() {
	        return React.createElement(
	            "div",
	            null,
	            "ab"
	        );
	    }
	});

	var ScrambleGame = React.createClass({
	    displayName: "ScrambleGame",

	    render: function render() {
	        return React.createElement(
	            "div",
	            null,
	            React.createElement(CharacterList, null),
	            React.createElement(WordList, null),
	            React.createElement(GuessingWord, null)
	        );
	    }
	});

	module.exports = ScrambleGame;

/***/ }
/******/ ]);