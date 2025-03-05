#!/usr/bin/env python3
import sys

current_country=None
current_total_deaths=0

for line in sys.stdin:
    #Tách dữ liệu theo tab
    parts=line.strip().split("\t")
    if len(parts) != 2:
        continue

    country, death_count=parts
    try:
        death_count=int(death_count)
    except ValueError:
        continue

    #Nếu vẫn đang xử lý cùng một quốc gia, cộng dồn số ca tử vong
    if country == current_country:
        current_total_deaths += death_count
    else:
        #Nếu gặp quốc gia mới, in kết quả cũ và đặt lại biến đếm
        if current_country:
            print(f"{current_country}\t{current_total_deaths}")

        current_country=country
        current_total_deaths=death_count

#In dòng cuối cùng
if current_country:
    print(f"{current_country}\t{current_total_deaths}")

