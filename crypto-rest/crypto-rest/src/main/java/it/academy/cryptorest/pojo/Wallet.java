package it.academy.cryptorest.pojo;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Wallet implements Serializable {

    @Id
    @Column(name = "wallet_id")
    private String id;
    @Column(name = "secret_key")
    private String secretKey;

    private long timestamp;



    //совпадение хеша wallet id + user password =  доступ (юзер пассворд  не храним нигде)

    /*
    алгоритм динамически меняемого хеша для проверки пароля

    1) в бд добавляем таймстамп последнего входа пользователя (при регистрации первый вход = последний)
    2) получаем день, месяц и год + время
    3) день - сдвиг каретки относительно символа с индексом 0
    4) месяц- количество символов, учавствующих в в преобразовании
    5) получаем хешкод sha3-512 (время + год)
    6) получаем строку с началом сдига и количетвом символов + хеш (время + год) и предпразуем ее в хеш sha3-512
    7) последние 4 символа - высылаются на моб тел







     */
}
