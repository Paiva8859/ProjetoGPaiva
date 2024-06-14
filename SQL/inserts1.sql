INSERT INTO public.carros (ano, cor, modelo, placa) VALUES
(2020, 'Vermelho', 'Toyota Corolla', 'ABC1D23'),
(2019, 'Azul', 'Honda Civic', 'EFG2H45'),
(2018, 'Preto', 'Ford Focus', 'IJK3L67'),
(2021, 'Branco', 'Chevrolet Cruze', 'MNO4P89'),
(2022, 'Prata', 'Hyundai Elantra', 'QRS5T01'),
(2017, 'Cinza', 'Volkswagen Jetta', 'UVW6X23'),
(2020, 'Verde', 'Nissan Sentra', 'YZA7B45'),
(2018, 'Amarelo', 'Kia Cerato', 'CDE8F67'),
(2019, 'Laranja', 'Renault Fluence', 'GHI9J89'),
(2021, 'Roxo', 'Peugeot 308', 'KLM0N01');

INSERT INTO public.clientes (cpf, data_nas, email, endereco, nome, telefone) VALUES
('123.456.789-00', '1990-01-01', 'joao@example.com', 'Rua A, 123', 'João Silva', '(11) 98765-4321'),
('987.654.321-00', '1985-05-15', 'maria@example.com', 'Rua B, 456', 'Maria Oliveira', '(21) 87654-3210'),
('456.789.123-00', '1978-08-30', 'pedro@example.com', 'Rua C, 789', 'Pedro Santos', '(31) 76543-2109'),
('321.654.987-00', '1992-11-25', 'ana@example.com', 'Rua D, 101', 'Ana Costa', '(41) 65432-1098'),
('789.123.456-00', '1980-02-10', 'carlos@example.com', 'Rua E, 202', 'Carlos Lima', '(51) 54321-0987'),
('159.753.486-00', '1987-03-12', 'lucas@example.com', 'Rua F, 303', 'Lucas Pereira', '(61) 43210-9876'),
('753.159.864-00', '1975-07-19', 'mariana@example.com', 'Rua G, 404', 'Mariana Rocha', '(71) 32109-8765'),
('951.357.258-00', '1995-12-21', 'juliana@example.com', 'Rua H, 505', 'Juliana Almeida', '(81) 21098-7654'),
('357.951.486-00', '1982-04-18', 'fernando@example.com', 'Rua I, 606', 'Fernando Mendes', '(91) 10987-6543'),
('258.357.159-00', '1988-09-14', 'paula@example.com', 'Rua J, 707', 'Paula Souza', '(95) 09876-5432');

INSERT INTO public.reservas (data_devolu, data_retirada, local_retirada, id_carro, id_cliente) VALUES
('2023-07-10', '2023-07-01', 'São Paulo', 1, 1),
('2023-08-20', '2023-08-10', 'Rio de Janeiro', 2, 2),
('2023-09-15', '2023-09-05', 'Belo Horizonte', 3, 3),
('2023-10-25', '2023-10-15', 'Curitiba', 4, 4),
('2023-11-10', '2023-11-01', 'Porto Alegre', 5, 5),
('2023-12-05', '2023-11-25', 'Florianópolis', 6, 6),
('2023-12-20', '2023-12-10', 'Brasília', 7, 7),
('2024-01-10', '2024-01-01', 'Salvador', 8, 8),
('2024-02-15', '2024-02-05', 'Recife', 9, 9),
('2024-03-05', '2024-02-25', 'Fortaleza', 10, 10);
