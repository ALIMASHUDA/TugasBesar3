## TUGAS BESAR PEMOGRAMAN LANJUT (KUIS SEDERHANA)

## ANGGOTA

- Muh. Farlan (202210370311106)
- Ali Mashuda (202210370311082)
- Muhammad Fito Nurdiyansyah (202210370311297)

## Deskripsi
Aplikasi Kuis (QuizApp) adalah sebuah program JavaFX sederhana yang memungkinkan pengguna untuk membuat, mengelola, dan memainkan kuis. Pengguna dapat menambahkan pertanyaan kuis, melihat daftar pertanyaan, dan memainkan kuis untuk melihat skor akhir. Aplikasi ini menggunakan antarmuka pengguna grafis (GUI) dengan bantuan JavaFX.

## Fitur Utama
1. **Tambah Kuis (Scene 1):** Pengguna dapat menambahkan pertanyaan kuis beserta jawaban dan poinnya melalui formulir di Scene 1. Terdapat validasi karakter maksimum untuk setiap kolom.
2. **Daftar Kuis (Scene 2):** Daftar pertanyaan kuis ditampilkan dalam tabel di Scene 2. Pengguna dapat memulai kuis, menghapus pertanyaan, dan menambah pertanyaan baru.
3. **Mulai Kuis (Scene 3):** Pengguna memasukkan nama dan dapat memulai kuis dengan menekan tombol "Mulai Kuis".
4. **Pemutakhiran Tabel Kuis (Scene 4):** Scene 4 menampilkan pertanyaan kuis saat ini dan memungkinkan pengguna untuk menjawab. Poin diberikan untuk jawaban benar.
5. **Ringkasan Kuis (Scene 5):** Setelah selesai menjawab, pengguna dapat melihat ringkasan kuis dengan jawaban benar, jawaban pengguna, dan poin yang diperoleh. Skor total ditampilkan, dan pengguna dapat memilih untuk keluar atau mengulangi kuis.

## Penggunaan Aplikasi
1. **Menambah Pertanyaan:**
    - Pada Scene 1, masukkan pertanyaan, jawaban, dan poin.
    - Tekan tombol "Tambah Kuis" untuk menyimpan pertanyaan.

2. **Melihat Daftar Pertanyaan:**
    - Pada Scene 2, lihat daftar pertanyaan dalam tabel.
    - Tekan tombol "Mulai Kuis" untuk memulai kuis atau "Hapus Kuis" untuk menghapus pertanyaan terpilih.

3. **Memulai Kuis:**
    - Pada Scene 3, masukkan nama dan tekan "Mulai Kuis" untuk memulai.

4. **Memainkan Kuis:**
    - Pada Scene 4, jawab pertanyaan yang ditampilkan.
    - Tekan "Submit Jawaban" untuk menjawab pertanyaan.
    - Setelah menjawab semua pertanyaan, pilih "Selesai Kuis" untuk melihat ringkasan.

5. **Ringkasan Kuis:**
    - Pada Scene 5, lihat jawaban benar, jawaban pengguna, dan poin di setiap pertanyaan.
    - Total poin ditampilkan bersama dengan opsi untuk keluar atau mengulangi kuis.

6. **Menghapus Pertanyaan:**
    - Pada Scene 2, pilih pertanyaan dan tekan "Hapus Kuis" untuk menghapusnya.

7. **Import Kuis:**
    - Pada Scene 2, tekan "Import Kuis" untuk mengimpor pertanyaan dari file `quiz_data.txt`.

8. **Kembali:**
    - Pada Scene 5, tekan "Kembali" untuk kembali ke Scene 2. Tabel pertanyaan dan ringkasan kuis dikosongkan.

9. **Keluar:**
    - Pada Scene 5, tekan "Keluar" untuk menutup aplikasi.

## Struktur Kode
- **QuizApp.java:** Kelas utama yang mengatur GUI dan logika aplikasi.
- **Quiz.java:** Kelas untuk merepresentasikan objek pertanyaan kuis.
- **quiz_data.txt:** File untuk menyimpan data pertanyaan kuis.
- **score.txt:** File untuk menyimpan skor pemain.

## Dependensi
- **JavaFX:** Digunakan untuk membuat antarmuka grafis.
- **Java IO dan NIO:** Digunakan untuk membaca dan menulis ke file.

## Catatan
- Gunakan karakter koma (`,`) sebagai pemisah kolom dalam file `quiz_data.txt`.

## Petunjuk Pengembangan
1. Pastikan Anda memiliki JavaFX terpasang dan dikonfigurasi dengan benar.
2. Sesuaikan file `quiz_data.txt` dengan format yang sesuai.
3. Jalankan aplikasi menggunakan metode yang sesuai pada lingkungan pengembangan Anda.

Selamat menggunakan aplikasi kuis! Jika ada masalah atau pertanyaan, jangan ragu untuk menghubungi pengembang.