import mysql.connector
import pandas as pd
cnx = mysql.connector.connect(user='root', password='admin', host='127.0.0.1', database='scramble_game')
cursor = cnx.cursor()
query = ("INSERT INTO word(word, index_data) values (%(word)s, %(index_data)s)")
words = pd.read_csv('.\words.txt')
words.columns = ['word']
for word in words['word']:
    try:
        print(word)
        data = {'word': word, 'index_data': word[0:3]}
        cursor.execute(query, data)
    except:
        print "Caught error"
cnx.commit()
cursor.close()
cnx.close()