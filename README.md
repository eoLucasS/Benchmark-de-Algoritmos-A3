<div align="center">

  # Benchmark de Algoritmos 

  Projeto em Java para comparar a eficiência de busca e ordenação em ArrayList e consultas SQL, analisando ciclos.

  [Nosso Objetivo](#nosso-objetivo) | [Stack](#stack) | [Colaboradores](#colaboradores) | [Contato](#contato)

  <img src="Project - A3/assets/to-readme/gif-demonstracao.gif" width="600" height="338">


  Ferramentas:

  ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
  ![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=CSS&logoColor=white)
  ![MySQL](https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
  
  Detalhes:
  
  ![Version](https://img.shields.io/badge/version-0.1.0-grey?style=flat&color=darkgrey)
  ![Stars](https://img.shields.io/github/stars/eoLucasS/Benchmark-de-Algoritmos-A3?style=flat&color=darkgrey)
  ![Repo Views](https://komarev.com/ghpvc/?username=eoLucasS&repo=Benchmark-de-Algoritmos-A3&color=lightgrey)
  ![Languages Count](https://img.shields.io/github/languages/count/eoLucasS/Benchmark-de-Algoritmos-A3?color=lightgrey)
  ![Top Language](https://img.shields.io/github/languages/top/eolucass/Benchmark-de-Algoritmos-A3?color=lightgrey)
  
</div>

## Nosso Objetivo

<div align="center">

 Comparar a quantidade de ciclos necessários para buscar ou ordenar por determinado jogo, comparando metodos como buscas linear e binary, e ordenações em bubble ou quick

</div>


## Funcionalidades

<details>
  <summary><b>Busca</b></summary>

  - Pesquisar um jogo pelo nome, completo ou parcial
  - Escolher método de busca: Linear ou Binary
    
</details>
<details>
  <summary><b>Ordenação</b></summary>

  - Ordenar lista por categoria (ID, Nome, Categoria, Data de Lançamento)
  - Escolher método de ordenação: Bubble Sort ou Quick Sort
    
</details>
<details>
  <summary><b>Comparação</b></summary>

  - Metodos são analisados contra SQL Queries
  - Resultado de cada método é exibido em ciclos de repetição
    
</details>

## Documentação:

- O código-fonte foi devidamente comentado usando o padrão JavaDoc para facilitar a compreensão das funcionalidades e métodos.
- A documentação do projeto que mostra o planejamento e funcionalidades e plano de desenvolvimento se encontram <a href="https://docs.google.com/document/d/1yQKc22IEB7giy-7p7YFWQ4cH8sxCx3jOVY9yD0VcSVg/edit?usp=sharing" target="_blank"> aqui </a>
- Os pré-requisitos do projeto tal como requisitados pela instituição se encontram Pré-Requisitos: <a href="https://mediacdns3.ulife.com.br/PAT/Upload/3681712/EDAAA3EspecificaodoProjeto_20240415180358.pdf" target="_blank"> aqui </a>

## Stack

<div align="center">

  | Camada | Ferramentas |
  |--------|-------------|
  | Front-End | JavaFX, Scene Builder |
  | Back-End | Java JDK 8 |
  | Data | MySQL, MySQL Connector J 8.4.0 |
  | Ambiente | Apache NetBeans IDE 16, XAMPP |

</div>

## Algoritmos de Ordenação (Sort)

<details><summary><b>BubbleSort</b></summary>

O bubble sort (ou ordenação por flutuação) é um algoritmo simples de ordenação que funciona comparando repetidamente pares de elementos adjacentes em uma lista e trocando suas posições quando estão na ordem errada. Esse processo é realizado em múltiplas passadas pela lista.

**Exemplo:**

Dada uma lista **4,1,3,2**, segue um exemplo de Bubble Sort:

<div align="center">

  | Passo | Par | Comparação | Ação | Nova lista |
  |-------|-----|------------|------|-------------|
  | 1 | (4,1) | 4 > 1 | troca | [1, 4, 3, 2] |
  | 2 | (4,3) | 4 > 3 | troca | [1, 3, 4, 2] |
  | 3 | (4,2) | 4 > 2 | troca | [1, 3, 2, 4] |
  | 4 | (1,3) | 1 < 3 | mantém | [1, 3, 2, 4] |
  | 5 | (3,2) | 3 > 2 | troca | [1, 2, 3, 4] |

</div>

Complexidade:

**Pior caso** (lista inversa): O(n²) comparações e trocas.
**Melhor caso** (já ordenada): O(n) com otimização (interrompe se nenhuma troca ocorrer).

</details>
<details><summary><b>QuickSort</b></summary>

O QuickSort (ou ordenação rápida) é um algoritmo de ordenação que utiliza a estratégia "dividir para conquistar". Ele funciona escolhendo um elemento chamado **pivô** e reorganizando a lista de forma que todos os elementos menores que o pivô fiquem à sua esquerda, e os maiores à sua direita. Esse processo é aplicado recursivamente às sublistas esquerda e direita.

**Exemplo:**

Dada uma lista **7, 9, 6, 5, 8**, segue um exemplo de QuickSort (pivô = último elemento):

<div align="center">

| Etapa | Sublistas | Pivô | Particionamento (menores, pivô, maiores) | Resultado |
|-------|----------|------|------------------------------------------|-----------|
| 1 | [7, 9, 6, 5, 8] | 8 | [7, 6, 5] , 8 , [9] | [7, 6, 5, 8, 9] |
| 2 | [7, 6, 5] | 5 | [] , 5 , [7, 6] | [5, 7, 6, 8, 9] |
| 3 | [7, 6] | 6 | [] , 6 , [7] | [5, 6, 7, 8, 9] |
| 4 | [7] | 7 | [] , 7 , [] | [5, 6, 7, 8, 9] |

</div>

Complexidade:

**Melhor caso** (pivô divide a lista ao meio): O(n log n)

**Pior caso** (pivô é sempre o menor ou maior elemento): O(n²)

</details>

## Algoritmos de Busca (Search)

<details><summary><b>Linear Search</b></summary>

A Busca Linear percorre cada elemento da lista sequencialmente até encontrar o valor desejado ou chegar ao final.

**Exemplo:**

Procurando o valor **6** na lista **[7, 9, 6, 5, 8]** :


<div align="center">

| índice | Valor |
| - | - |
| 0 | 7 |
| 1 | 9 |
| 2 | 6 |
| 3 | 5 |
| 4 | 8 |

</div>

O passo a passo seria assim:

<div align="center">

| Passo | Índice | Elemento | Comparação | Resultado |
|-------|--------|----------|------------|-----------|
| 1 | 0 | 7 | 7 == 6? | ❌ não |
| 2 | 1 | 9 | 9 == 6? | ❌ não |
| 3 | 2 | 6 | 6 == 6? | ✅ **encontrado** |

</div>

> Note que, caso seja realizada uma

Complexidade:

**Melhor caso** (Elemento no início da lista): O(1)
**Pior caso** (Elemento no final ou ausente): O(n)

</details>
<details><summary><b>Binary Search</b></summary>

A Busca Binária localiza um elemento em uma **lista ordenada** reduzindo pela metade o intervalo de busca a cada iteração.

**Exemplo:**

Procurando o valor **14** na lista **[11, 12, 13, 14, 15, 16]** :

<div align="center">

| Índice | 0 | 1 | 2 | 3 | 4 | 5 |
|--------|---|---|---|---|---|---|
| Valor | 11 | 12 | 13 | 14 | 15 | 16 |

</div>

**Visualização do intervalo de busca:**

<div align="center">

| Passo | Intervalo ativo | Meio | Comparação | Novo intervalo |
|-------|----------------|------|------------|----------------|
| 1 | [11, 12, 13, 14, 15, 16] | 13 | 13 < 14 | [14, 15, 16] |
| 2 | [14, 15, 16] | 15 | 15 > 14 | [14] |
| 3 | [14] | 14 | 14 == 14 | ✅ encontrado |

</div>

 **Importante:**
 >A Busca Binária exige que a lista esteja **ordenada** previamente. Caso contrário, o algoritmo não funciona corretamente.

Complexidade:

**Melhor caso** (Elemento exatamente no meio): O(1)

**Pior caso** (Elemento em qualquer posição ou ausente): O(log n)

</details>

## Instalação e Uso

```bash
# 1. Baixe o projeto ou faça um clone do repositório
git clone <url-do-repositorio>

# 2. Abra a pasta "Project - A3" na sua IDE de preferência (recomendamos o NetBeans)
cd "Project - A3"

# 3. Certifique-se de que o arquivo "games.csv" está presente no diretório de execução na pasta "data"
ls data/games.csv

# 4. Compile e execute o projeto na IDE
# No NetBeans: Clique em "Run Project" (F6)
# Ou via linha de comando (se for um projeto Java):
javac src/*.java
java -cp src Main

# 5. O software abrirá diretamente na tela do usuário, permitindo interagir com as funcionalidades disponíveis
echo "Software em execução - aguardando interação do usuário"
```

## Contato

<p align="center">

 <a href="https://www.linkedin.com/in/lucaslopesdasilva/" alt="Linkedin">
  <img src="https://img.shields.io/badge/-Linkedin-000?style=for-the-badge&logo=Linkedin&logoColor=0A66C2&link=https://www.linkedin.com/in/lucaslopesdasilva/"/> 
 </a>
  
 <a href="https://twitter.com/eoLucasS114" alt="Twitter">
  <img src="https://img.shields.io/badge/-Twitter-000?style=for-the-badge&logo=Twitter&logoColor=1DA1F2&link=https://twitter.com/eoLucasS114"/> 
 </a>

 <a href="https://portfolio-lucaslopes.vercel.app/" alt="Portfolio">
  <img src="https://img.shields.io/badge/my_portfolio-000?style=for-the-badge&logo=ko-fi&logoColor=FFF&link=https://portfolio-lucaslopes.vercel.app/"/>
 </a>

 </p>
 
## Colaboradores

<div align="center">

| Lucas Silva | Nycolas Garcia | Danilo Santos | Breno Melo | Gustavo Henrique |
|-------------|----------------|---------------|------------|------------------|
| <img src="https://avatars.githubusercontent.com/u/119815116?v=4" width="100px;" /> | <img src="https://avatars.githubusercontent.com/u/127459801?v=4" width="100px;" /> | <img src="https://avatars.githubusercontent.com/u/110133245?v=4" width="100px;" /> | <img src="https://avatars.githubusercontent.com/u/75175303?v=4" width="100px;" /> | <img src="https://avatars.githubusercontent.com/u/72585011?v=4" width="100px;" /> |
| <a href="https://www.linkedin.com/in/lucaslopesdasilva/"><img src="https://skills.syvixor.com/api/icons?i=linkedin" width="20" /> LinkedIn</a> | <a href="https://www.linkedin.com/in/nycolasagrgarcia/"><img src="https://skills.syvixor.com/api/icons?i=linkedin" width="20" /> LinkedIn</a> | <a href="https://www.linkedin.com/in/danilodoes/"><img src="https://skills.syvixor.com/api/icons?i=linkedin" width="20" /> LinkedIn</a> | <a href="https://www.linkedin.com/in/breno-melo-53822a20a/"><img src="https://skills.syvixor.com/api/icons?i=linkedin" width="20" /> LinkedIn</a> | <a href="https://www.linkedin.com/in/gustavo-henrique-dev/"><img src="https://skills.syvixor.com/api/icons?i=linkedin" width="20" /> LinkedIn</a> |
</div>

-----

<h3 align="center"> Desenvolvido por <a href="https://www.linkedin.com/in/lucaslopesdasilva/">Lucas Lopes da Silva</a> ☕</h3>
