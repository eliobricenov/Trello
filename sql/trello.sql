-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-07-2018 a las 13:11:49
-- Versión del servidor: 10.1.21-MariaDB
-- Versión de PHP: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `trello`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `boards`
--

CREATE TABLE `boards` (
  `board_id` int(11) NOT NULL,
  `board_name` varchar(30) NOT NULL,
  `user_id` int(11) NOT NULL,
  `board_created_at` datetime NOT NULL,
  `board_color` varchar(10) NOT NULL,
  `board_description` varchar(60) CHARACTER SET utf8 COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `boards`
--

INSERT INTO `boards` (`board_id`, `board_name`, `user_id`, `board_created_at`, `board_color`, `board_description`) VALUES
(55, 'Prueba Tablero', 5, '2018-07-08 04:09:26', '#8fff00', 'This is my first description.'),
(64, 'tabla 4', 5, '2018-07-12 07:13:37', '#8fff00', 'asdas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cards`
--

CREATE TABLE `cards` (
  `card_id` int(11) NOT NULL,
  `column_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `card_name` varchar(25) NOT NULL,
  `card_description` varchar(252) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `cards`
--

INSERT INTO `cards` (`card_id`, `column_id`, `user_id`, `card_name`, `card_description`) VALUES
(8, 86, 10, 'card 1', 'dasdasdasd'),
(9, 86, 10, 'egfefv', 'dasdasd');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `columns`
--

CREATE TABLE `columns` (
  `column_id` int(11) NOT NULL,
  `board_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `column_name` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `columns`
--

INSERT INTO `columns` (`column_id`, `board_id`, `user_id`, `column_name`) VALUES
(82, 55, 5, 'dios mio'),
(86, 55, 10, 'column 4'),
(87, 55, 10, 'column 4');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comments`
--

CREATE TABLE `comments` (
  `comment_id` int(11) NOT NULL,
  `card_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `comment_text` varchar(250) NOT NULL,
  `comment_created_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `files`
--

CREATE TABLE `files` (
  `file_id` int(11) NOT NULL,
  `card_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `file_url` varchar(250) NOT NULL,
  `file_uploaded_at` datetime NOT NULL,
  `file_name` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `type_board_user`
--

CREATE TABLE `type_board_user` (
  `type_board_user_id` int(11) NOT NULL,
  `type_board_user_description` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `type_board_user`
--

INSERT INTO `type_board_user` (`type_board_user_id`, `type_board_user_description`) VALUES
(1, 'Board Master'),
(2, 'Collaborator');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `type_user`
--

CREATE TABLE `type_user` (
  `type_id` int(11) NOT NULL,
  `type_des` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `type_user`
--

INSERT INTO `type_user` (`type_id`, `type_des`) VALUES
(1, 'Normal User');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `user_username` varchar(25) NOT NULL,
  `user_last_name` varchar(30) NOT NULL,
  `user_email` varchar(50) NOT NULL,
  `user_password` varchar(60) NOT NULL,
  `user_created_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`user_id`, `type_id`, `user_name`, `user_username`, `user_last_name`, `user_email`, `user_password`, `user_created_at`) VALUES
(2, 1, 'Jesus', 'ggsus', 'Urdaneta', 'ggsus@gmail.com', '$2a$10$OOFXOks/p79mqxZUwxlZweA/V', '2018-06-26 22:56:50'),
(4, 1, 'Darianna', 'dariplinares', 'Linares', 'dariplinares@gmail.com', '$2a$10$7soJGPMbkQRJLKZqsxEaOe9EhkFS13ZUcam8WhZm88yld1ms2EMzC', '2018-06-27 01:25:33'),
(5, 1, 'Elio', 'eliobricenov', 'Briceno', 'eliobricenov@gmail.com', '$2a$10$XvT/3/Yr9BiNvoXWgRt9muVSl.bHcEAd4CLct6jK48AKMVykq8jZ2', '2018-06-27 01:43:59'),
(6, 1, 'Mabelis', 'mabevillalobos', 'Villalobos', 'mabevillalobos@gmail.com', '$2a$10$wJ7/AXAUuPVcjH1AVBUdBOfaaOD2eaW5U45dK8d1EZk53knm23lbC', '2018-06-30 17:15:23'),
(7, 1, 'Mariela', 'villalobosm25', 'Villalobos', 'villalobosm25@hotmail.com', '$2a$10$yAIOkZed6mRKXxN3PnUYRefZhjCWX9NLczY9FPMNHLDTcqasSzFGS', '2018-07-01 18:34:00'),
(8, 1, 'Nestor', 'elsir', 'Burgos', 'elsir@gmail.com', '$2a$10$bis46tN6iaWNoOQXeT6MSuB9zRaqOm2t0lUaV5kumhxSe5zPE6RSm', '2018-07-02 13:27:48'),
(9, 1, 'Paola', 'paolabricenov', 'Briceno', 'paolabricenov@gmail.com', '$2a$10$3F1g61HUzMTzVSgnMXw4DuM5ljR74xUmmCP3ZL31/Ywm1iggxMm7i', '2018-07-03 21:24:20'),
(10, 1, 'Jose', 'joropeza', 'Oropeza', 'joropeza@gmail.com', '$2a$10$fbYXDXh7ZCDbKAsDkaK12usrSP476YT3u57z.BQeiBedIqL5iTvKm', '2018-07-04 11:05:23');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user_board`
--

CREATE TABLE `user_board` (
  `board_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `type_board_user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `user_board`
--

INSERT INTO `user_board` (`board_id`, `user_id`, `type_board_user_id`) VALUES
(55, 5, 1),
(64, 5, 1),
(64, 4, 2),
(64, 8, 2),
(55, 10, 2);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `boards`
--
ALTER TABLE `boards`
  ADD PRIMARY KEY (`board_id`),
  ADD KEY `users_boards_fk` (`user_id`);

--
-- Indices de la tabla `cards`
--
ALTER TABLE `cards`
  ADD PRIMARY KEY (`card_id`),
  ADD KEY `users_cards_fk` (`user_id`),
  ADD KEY `columns_cards_fk` (`column_id`);

--
-- Indices de la tabla `columns`
--
ALTER TABLE `columns`
  ADD PRIMARY KEY (`column_id`),
  ADD KEY `boards_columns_fk` (`board_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indices de la tabla `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`comment_id`),
  ADD KEY `users_comments_fk` (`user_id`),
  ADD KEY `cards_comments_fk` (`card_id`);

--
-- Indices de la tabla `files`
--
ALTER TABLE `files`
  ADD PRIMARY KEY (`file_id`),
  ADD KEY `users_files_fk` (`user_id`),
  ADD KEY `cards_files_fk` (`card_id`);

--
-- Indices de la tabla `type_board_user`
--
ALTER TABLE `type_board_user`
  ADD PRIMARY KEY (`type_board_user_id`);

--
-- Indices de la tabla `type_user`
--
ALTER TABLE `type_user`
  ADD PRIMARY KEY (`type_id`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD KEY `type_user_users_fk` (`type_id`);

--
-- Indices de la tabla `user_board`
--
ALTER TABLE `user_board`
  ADD KEY `type_board_user_user_board_fk` (`type_board_user_id`),
  ADD KEY `users_user_board_fk` (`user_id`),
  ADD KEY `boards_user_board_fk` (`board_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `boards`
--
ALTER TABLE `boards`
  MODIFY `board_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=65;
--
-- AUTO_INCREMENT de la tabla `cards`
--
ALTER TABLE `cards`
  MODIFY `card_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT de la tabla `columns`
--
ALTER TABLE `columns`
  MODIFY `column_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=88;
--
-- AUTO_INCREMENT de la tabla `comments`
--
ALTER TABLE `comments`
  MODIFY `comment_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `files`
--
ALTER TABLE `files`
  MODIFY `file_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `type_board_user`
--
ALTER TABLE `type_board_user`
  MODIFY `type_board_user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `type_user`
--
ALTER TABLE `type_user`
  MODIFY `type_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cards`
--
ALTER TABLE `cards`
  ADD CONSTRAINT `columns_cards_fk` FOREIGN KEY (`column_id`) REFERENCES `columns` (`column_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `users_cards_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `columns`
--
ALTER TABLE `columns`
  ADD CONSTRAINT `boards_columns_fk` FOREIGN KEY (`board_id`) REFERENCES `boards` (`board_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `columns_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `cards_comments_fk` FOREIGN KEY (`card_id`) REFERENCES `cards` (`card_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `users_comments_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `files`
--
ALTER TABLE `files`
  ADD CONSTRAINT `cards_files_fk` FOREIGN KEY (`card_id`) REFERENCES `cards` (`card_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `users_files_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `type_user_users_fk` FOREIGN KEY (`type_id`) REFERENCES `type_user` (`type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `user_board`
--
ALTER TABLE `user_board`
  ADD CONSTRAINT `boards_user_board_fk` FOREIGN KEY (`board_id`) REFERENCES `boards` (`board_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `type_board_user_user_board_fk` FOREIGN KEY (`type_board_user_id`) REFERENCES `type_board_user` (`type_board_user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `users_user_board_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
