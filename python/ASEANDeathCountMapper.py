#!/usr/bin/env python3
import sys

#Danh sách các quốc gia ASEAN
ASEAN_COUNTRIES={
    "Brunei Darussalam", "Cambodia", "Indonesia", "Lao People's Democratic Republic",
    "Malaysia", "Myanmar", "Philippines", "Singapore", "Thailand", "Viet Nam"
}

#Bỏ qua dòng tiêu đề
is_header=True

for line in sys.stdin:
    if is_header:
        is_header=False
        continue

    fields=line.strip().split("\t")
    if len(fields) < 8:
        continue

    country=fields[0].strip()  #Cột tên quốc gia
    deaths_cumulative=fields[7].strip()  #Cột số ca tử vong tích lũy

    #Debug log để kiểm tra quốc gia và số ca tử vong
    if country not in ASEAN_COUNTRIES:
        continue  #Bỏ qua các quốc gia không trong ASEAN
    
    try:
        #Chuyển đổi số ca tử vong sang kiểu số nguyên
        death_count=int(float(deaths_cumulative.replace(",", "")))
        print(f"{country}\t{death_count}")
    except ValueError:
        #Nếu có lỗi trong chuyển đổi, bỏ qua
        continue

