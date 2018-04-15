import json, pymysql
#
def importPrecinct(d, db):
    cursor = db.cursor()
    for i in d["features"]:
        cdNumber = str(i["properties"]["CONGRESSIO"])
        if cdNumber == "NA":
            cdNumber = "0"
        sql = """INSERT INTO Precinct( pid, population, cdNumber, aLand, aWater, totalVote, year, latitude,
                    longtitude, rVote, dVote, oVote, isBorder, avgRace) values (
                    """+str(i["pid"])+""","""+str(i["properties"]["POP100"])+""","""+cdNumber+""",
                    """+str(i["properties"]["ALAND10"])+""","""+str(i["properties"]["AWATER10"])+""",
                    """+str(i["properties"]["T_08"])+""",2008,"""+str(i["properties"]["INTPTLAT10"])+""",
                    """+str(i["properties"]["INTPTLON10"])+""","""+str(i["properties"]["PRES_R_08"])+""",
                    """+str(i["properties"]["PRES_D_08"])+""",
                    """+str((i["properties"]["T_08"]-i["properties"]["PRES_R_08"]-i["properties"]["PRES_D_08"]))+""",
                    0,"""+str(i["properties"]["AV"])+""");"""
        try:
            cursor.execute(sql)
            db.commit()
        except:
            print("error1")
            db.rollback()
    return