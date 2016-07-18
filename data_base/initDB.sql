CREATE TABLE word
(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	word varchar(50) unique key NOT NULL,
    index_data varchar(10)
);
ALTER TABLE `word` ADD INDEX `index_index_data` (`index_data`);

insert into word(word, index_data) values('set', 'set');
insert into word(word, index_data) values('setter', 'setter');
insert into word(word, index_data) values('test', 'test');
insert into word(word, index_data) values('east', 'eas');
insert into word(word, index_data) values('western', 'wes');
insert into word(word, index_data) values('west', 'wes');
insert into word(word, index_data) values('whom', 'who');
insert into word(word, index_data) values('who', 'who');
insert into word(word, index_data) values('eat', 'eat');
insert into word(word, index_data) values('worst', 'wor');
insert into word(word, index_data) values('sat', 'sat');
