var React = require("react");
var $ = require("jquery");

var ReactDOM = require('react-dom');

var Level = React.createClass({
    render: function(){
        return (<div>Level: {this.props.level}</div>);
    }
})
var CharacterList = React.createClass({
    render: function(){
        var characterNodes = this.props.characters.map(function(character){
            return (<span>{character} </span>);
        });
        return (<h3>Characters: {characterNodes}</h3>);
    }
});

var WordList = React.createClass({
    render: function(){
        var wordNodes = this.props.words.map(function(word)
        {
            return (<li key={word}>{word}</li>);
        });
        return (
            <ul>
                {wordNodes}
            </ul>
        );
    }
});

var GuessingWord = React.createClass({
    render: function(){
        var invalidText = null;
        if(this.props.invalid) {
            invalidText = (<div>Invalid word</div>);
        }
        return (
        <div>
            <h3>Your guess: {this.props.word}</h3>
            {invalidText}
         </div>
         );
    }
});

var GiveUpInfoControl = React.createClass({
    render: function(){
        var wordListNode = null;
        if(this.props.words.length > 0) {
            wordListNode = (<div>
                                <div>You miss {this.props.missingWordsCount} word(s)</div>
                                <div>All possible words: </div>
                                <WordList words={this.props.words}/>
                            </div>)
        }
        return (
            <div>
                {wordListNode}
             </div>
        )
    }
});

var ScrambleGame = React.createClass({
    getInitialState: function(){
        return {guessingWord: '', checkedWords: [], characters: [], initialCharacters: [], level: 0, invalid: false, words: []};
    },
    render: function(){
        return (
            <div>
                <h2>Scramble Game</h2>
                <Level level={this.state.level} />
                <CharacterList characters={this.state.characters}/>
                <GiveUpInfoControl  words={this.state.words} missingWordsCount={this.state.words.length - this.state.checkedWords.length}/>
                <div>Your correct answers:
                    <WordList words={this.state.checkedWords}/>
                </div>
                <GuessingWord word={this.state.guessingWord} invalid={this.state.invalid}/>
                <button onClick={this.onGiveUpClick}>Give up</button>
            </div>
        );
    },
    onGiveUpClick: function(e){
        $.get('/words/all',{}, function(data){
            this.setState({words: data.words});
        }.bind(this));
        e.preventDefault();
        e.target.blur();
    },
    onKeyPressed: function(e){
        switch (e.which)
        {
            case 8:
            {
                 e.preventDefault();
                this.deleteLastCharacterFromGuessingWord();
                break;
            }
            case 13:
            {
                this.submitWord();
                break;
            }
            default:
            {
                if(('A'.charCodeAt() <= e.which && e.which <= 'Z'.charCodeAt()) ||
                 ('a'.charCodeAt() <= e.which && e.which <= 'z'.charCodeAt())) {
                    this.validCharacterPressed(String.fromCharCode(e.which).toLowerCase());
                }
            }
        }
    },
    deleteLastCharacterFromGuessingWord: function() {
        if(this.state.guessingWord.length > 0) {
            var character = this.state.guessingWord.substring(this.state.guessingWord.length - 1);
            var newGuessingWord = this.state.guessingWord.substring(0, this.state.guessingWord.length - 1);
            this.state.characters.push(character);
            this.setState({guessingWord: newGuessingWord, characters: this.state.characters, invalid: false});
        }
    },
    submitWord: function(){
        if(this.state.guessingWord.length == 0) {
            return;
        }
        $.ajaxSetup({ traditional: true });
        $.get('/words',{ checkingWord: this.state.guessingWord, checkedWords: this.state.checkedWords }, function(data){
           if(data.valid) {
                var checkedWords = this.state.checkedWords;
                if(checkedWords.indexOf(data.checkedWord) == -1) {
                    checkedWords.push(data.checkedWord);
                }
                if(data.nextCharacters){
                    var characters = this.shallowCopyArray(data.nextCharacters);
                    this.setState({guessingWord: '', checkedWords: [], characters: characters,
                    initialCharacters: data.nextCharacters, level: this.state.level + 1, words: []});
                }else{
                    var characters = this.shallowCopyArray(this.state.initialCharacters);
                    this.setState({guessingWord: '', checkedWords: checkedWords, characters: characters});
                }

           }else{
            this.setState({invalid: true});
           }
        }.bind(this));
    },
    validCharacterPressed: function(character){
        var characters = this.state.characters;
        var index = characters.indexOf(character);
        if(index > -1){
            characters.splice(index, 1);
            var guessingWord = this.state.guessingWord + character;
            this.setState({guessingWord: guessingWord, characters: characters, invalid: false})
        }
    },
    componentDidMount: function(){
        this.loadCharacters();
        $(document).on("keydown", this.onKeyPressed);
    },
    shallowCopyArray: function(srcArray) {
        var descArray = new Array(srcArray.length);
        for(var index = 0; index < srcArray.length; ++ index) {
            descArray[index] = srcArray[index];
        }
        return descArray;
    },
    loadCharacters: function(){
        $.get('/words/characters',{}, function(data){
            var characters = this.shallowCopyArray(data.characters);
            this.setState({characters: characters, initialCharacters: data.characters});
         }.bind(this));
    }
});

ReactDOM.render(
  <ScrambleGame  />,
  document.getElementById('content')
);
module.exports = ScrambleGame;