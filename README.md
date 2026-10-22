# Implementação de Hash Tables em Java (trabalho acadêmico)
Em ambas as implementações, a tabela hash é criada como um vetor de Nodes (classes que encapsulam os valores que serão armazenados, neste caso inteiros). A função hash aplicada às chaves é simples, consistindo apenas em uma operação módulo (%) entre a chave e a capacidade do vetor.

## Hash Table Encadeado
A cada colisão, o novo Node (nó) que será inserido é encadeado de forma ordenada a partir do Node já presente na posição em que houve a colisão, formando assim uma lista encadeada simples ordenada.

## 'ReHash' Table
A cada colisão, o novo Node inserido tem sua posição na tabela recalculada através de chamadas da função **reHash**. A cada nova chave gerada, uma nova tentativa de inserção é realizada. Caso não hajam mais posições disponíveis, o Node é descartado.
