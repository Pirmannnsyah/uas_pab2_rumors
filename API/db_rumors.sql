-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 07 Jan 2023 pada 14.39
-- Versi server: 10.4.27-MariaDB
-- Versi PHP: 8.0.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_rumors`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `coment`
--

CREATE TABLE `coment` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `post_id` bigint(20) UNSIGNED NOT NULL,
  `coment` text NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `like`
--

CREATE TABLE `like` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `post_id` bigint(20) UNSIGNED NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `post`
--

CREATE TABLE `post` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `kategori` enum('news','games','sports','foods') NOT NULL,
  `judul` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `user_id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `username` varchar(20) NOT NULL,
  `profile_picture` text DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `coment`
--
ALTER TABLE `coment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `coment_post_id_foreign` (`post_id`);

--
-- Indeks untuk tabel `like`
--
ALTER TABLE `like`
  ADD PRIMARY KEY (`id`),
  ADD KEY `like_post_id_foreign` (`post_id`);

--
-- Indeks untuk tabel `post`
--
ALTER TABLE `post`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `users_username_unique` (`username`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `coment`
--
ALTER TABLE `coment`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `like`
--
ALTER TABLE `like`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `post`
--
ALTER TABLE `post`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `coment`
--
ALTER TABLE `coment`
  ADD CONSTRAINT `coment_post_id_foreign` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`);

--
-- Ketidakleluasaan untuk tabel `like`
--
ALTER TABLE `like`
  ADD CONSTRAINT `like_post_id_foreign` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
