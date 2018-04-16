import json, pymysql
#
def importCoordinate(d, db):
    cursor = db.cursor()
    for i in d["features"]:
        for j in i["geometry"]["coordinates"][0]:

            sql = """INSERT INTO coordinate(precinctid, x, y) values (
                        """+str(i["pid"])+""",
                        """+str(j[0])+""",
                        """+str(j[1])+"""
                        );"""
            try:
                cursor.execute(sql)
                db.commit()
            except:
                print("error4")
                db.rollback()
        print(i["pid"])
    return