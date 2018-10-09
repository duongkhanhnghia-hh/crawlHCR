﻿# crawlHCR


# Các url crawl:
- Domain: https://www.hackerrank.com/dashboard
- List bài tập (ví dụ): https://www.hackerrank.com/rest/contests/master/tracks/algorithms/challenges?offset=20&limit=10&track_login=true
- Nội dung bài tập (ví dụ): https://www.hackerrank.com/challenges/day-of-the-programmer/problem
- Leaderboard (ví dụ): https://www.hackerrank.com/rest/contests/master/challenges/day-of-the-programmer/leaderboard?offset=20&limit=20&include_practice=true

# Luồng ứng dụng:
- Crawl danh sách bài tập, đề bài, leaderboard lưu vào sqlite vì dữ liệu lớn nên hiện tại em chỉ crawl 100 leaderboard đầu tiền cho mỗi bài tập
- Xuất dữ liệu từ database ra file data.js dưới dạng json và gán cho biến data
- Sử dụng javascript, html xây dựng view, lấy dữ liệu từ biến data

# Database
- Database gồm 5 bảng: Domain, Exercise, Sample, User, User_Ex
- Dữ liệu đã crawl lưu trong file hcr_fullData.db và nén thành hcr_fullData.zip
- Dữ liệu để test crawl lưu trong file hcr_noData.db

# Cách chạy phần View:
- Giải nén view/data.zip và copy file data.js vào thư mục view
- Chạy file CrawlView.html trong thư mục view

# Cách chạy phần Crawl:
- Chạy class MainCrawl trong package crawl để tiến hành crawl
- Dữ liệu sẽ được lưu vào database hcr_noData.db
- Chạy class SaveResult trong package result để tiến hành xuất dữ liệu ra file data.js
