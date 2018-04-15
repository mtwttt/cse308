import json, pymysql
#
def getCdDict(d):
    cdDict = {}
    for i in d["features"]:
        if i["properties"]["CONGRESSIO"] != "NA" :
            if i["properties"]["CONGRESSIO"] not in cdDict:
                cdDict[i["properties"]["CONGRESSIO"]] = {"totalPopulation":0,"totalRacial":0,
                                                         "rVote":0, "dVote": 0, "countP": 0, "totalVote": 0}
            cdDict[i["properties"]["CONGRESSIO"]]["totalPopulation"] += i["properties"]["POP100"]
            cdDict[i["properties"]["CONGRESSIO"]]["totalRacial"] += i["properties"]["AV"]
            cdDict[i["properties"]["CONGRESSIO"]]["rVote"] += i["properties"]["PRES_R_08"]
            cdDict[i["properties"]["CONGRESSIO"]]["dVote"] += i["properties"]["PRES_D_08"]
            cdDict[i["properties"]["CONGRESSIO"]]["countP"] += 1
            cdDict[i["properties"]["CONGRESSIO"]]["totalVote"] += i["properties"]["T_08"]
    return cdDict

def importCD(d, sid, db):
    cdDict = getCdDict(d)
    cursor = db.cursor()
    for cdid in cdDict.keys():
        whichParty = "d"
        if cdDict[cdid]["rVote"] > cdDict[cdid]["dVote"]:
            whichParty = "r"
        sql = """INSERT INTO CongressionalDistrict( cdid, totalPopulation, sid, whichParty,totalVote, republicanVote, 
                        democratVote, year, compactnessRatio, representativeAt, totalRacial
                        ) values ( """+cdid+""", """+str(cdDict[cdid]["totalPopulation"]) +""",
                        """+sid +""", \""""+whichParty +"""\", 
                        """+str(cdDict[cdid]["totalVote"])+""", 
                        """+str(cdDict[cdid]["rVote"]) +""",
                        """+str(cdDict[cdid]["dVote"]) +""", 2008, 0, 0,
                        """+str(cdDict[cdid]["totalRacial"]) +""");"""
        try:
            cursor.execute(sql)
            db.commit()
        except:
            print("error2")
            db.rollback()