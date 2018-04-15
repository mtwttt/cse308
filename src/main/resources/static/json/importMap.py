import json, pymysql, sys
import importState, importCD, importPrecinct

KANSAS = 1
COLORADO = 2
UTAH = 3

def addPid(d,fileName):
    for i in d["features"]:
        if "pid" not in i:
            i["pid"] = count
            count += 1
    with open(fileName, 'w') as outfile:
        d = json.dump(d, outfile, indent=1)
    return

def main():
    fileName = sys.argv[1]
    with open(fileName) as json_data:
        d = json.load(json_data)
    addPid(d, fileName)
    db = pymysql.connect(host='mysql4.cs.stonybrook.edu', user='zhzou',
                         password='109825816', db='zsyj', charset='utf8mb4',)
    if "kansas" in fileName:
        sid = KANSAS
    elif "utah" in fileName:
        sid = UTAH
    else:
        sid = COLORADO
    importState.importState(d,fileName[0:-5],str(sid),db)
    importCD.importCD(d,str(sid),db)
    importPrecinct.importPrecinct(d,db)
    db.close()

if __name__ == "__main__":
    main()