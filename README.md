# crawlHCR


# Các url crawl:
- Domain: https://www.hackerrank.com/dashboard
- List bài tập (ví dụ): https://www.hackerrank.com/rest/contests/master/tracks/algorithms/challenges?offset=20&limit=10&track_login=true
- Nội dung bài tập (ví dụ): https://www.hackerrank.com/challenges/day-of-the-programmer/problem
- Leaderboard (ví dụ): https://www.hackerrank.com/rest/contests/master/challenges/day-of-the-programmer/leaderboard?offset=20&limit=20&include_practice=true

# Luồng ứng dụng:
- Crawl danh sách bài tập, đề bài, leaderboard lưu vào sqlite vì dữ liệu lớn nên hiện tại em chỉ crawl 100 leaderboard đầu tiền cho mỗi bài tập
- Xuất dữ liệu từ database ra file data.js dưới dạng json gán cho biến data
- Sử dụng javascript, html xây dựng view lấy dữ liệu từ biến data

# Database
- Database gồm 5 bảng: Domain, Exercise, Sample, User, User_Ex
- Vì giới hạn lưu lượng file của github là 50 MB mà dung lượng của database lớn hơn nên em đã nén database lại, anh giải nén là chạy được ạ

# Cách chạy phần View:
- Giải nén data.zip và copy file data.js vào thư mục view
- Chạy file CrawlView.html trong thư mục view

# Cách chạy phần Crawl:
- Giải nén hcr.zip và copy file hcr.db vào thư mục gốc
- Tạo database theo database có sẵn: hcr.db
- Cập nhật biến URL_SQLite trong class Config theo đường dẫn của database
- Chạy class MainCrawl trong package crawl để tiến hành crawl
- Dữ liệu sẽ được lưu vào database
- Chạy class SaveResult trong package result để tiến hành xuất dữ liệu ra file data.js
