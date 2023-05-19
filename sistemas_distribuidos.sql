-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 19/05/2023 às 21:53
-- Versão do servidor: 10.4.28-MariaDB
-- Versão do PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `sistemas_distribuidos`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `incidentes`
--

CREATE TABLE `incidentes` (
  `IDIncidente` bigint(20) UNSIGNED NOT NULL,
  `Data_Incidente` varchar(50) NOT NULL,
  `Hora_Incidente` varchar(50) NOT NULL,
  `Estado` varchar(50) NOT NULL,
  `Cidade` varchar(50) NOT NULL,
  `Bairro` varchar(50) NOT NULL,
  `Tipo_Incidente` varchar(50) NOT NULL,
  `IDUsuario` int(11) NOT NULL,
  `Rua` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `incidentes`
--

INSERT INTO `incidentes` (`IDIncidente`, `Data_Incidente`, `Hora_Incidente`, `Estado`, `Cidade`, `Bairro`, `Tipo_Incidente`, `IDUsuario`, `Rua`) VALUES
(3, '2002-03-26', '26:10', 'ESTADOS', 'Paranagua', 'DAD', 'INCIDENTE', 1, ''),
(4, '2002-03-26', '14:00', 'ESTADOS', 'PARANAGUA', 'DAD', 'Alagamento', 1, 'WASHINGTON');

-- --------------------------------------------------------

--
-- Estrutura para tabela `usuario`
--

CREATE TABLE `usuario` (
  `IDUsuario` bigint(20) UNSIGNED NOT NULL,
  `Nome` varchar(50) NOT NULL,
  `Email` varchar(60) NOT NULL,
  `Senha` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `usuario`
--

INSERT INTO `usuario` (`IDUsuario`, `Nome`, `Email`, `Senha`) VALUES
(1, 'Teste', 'teste@teste', 'vguvg'),
(3, 'TESTE 2', 'teste@teste.com', '123456'),
(4, 'Vinicius', 'vinicius@teste.com', '345678'),
(5, 'testezinho', 'abc@abc', '345678'),
(6, 'eduardo', 'eduardo@email', '345678'),
(7, 'testeteste', 'testeteste@teste', '345678'),
(8, 'abcabc', 'email@email', '345678');

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `incidentes`
--
ALTER TABLE `incidentes`
  ADD UNIQUE KEY `IDIncidente` (`IDIncidente`);

--
-- Índices de tabela `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`IDUsuario`),
  ADD UNIQUE KEY `ID` (`IDUsuario`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `incidentes`
--
ALTER TABLE `incidentes`
  MODIFY `IDIncidente` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de tabela `usuario`
--
ALTER TABLE `usuario`
  MODIFY `IDUsuario` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
