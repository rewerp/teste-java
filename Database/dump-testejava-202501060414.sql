PGDMP                          }         	   testejava    15.2    15.2                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                        0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            !           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            "           1262    24661 	   testejava    DATABASE     �   CREATE DATABASE testejava WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE testejava;
                postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                pg_database_owner    false            #           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   pg_database_owner    false    4            �            1255    32929    atualizar_valor_total_venda()    FUNCTION     �  CREATE FUNCTION public.atualizar_valor_total_venda() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    -- Atualiza o campo valor_total na tabela VENDAS com a soma dos itens
    UPDATE vendas
    SET valor_total = (
        SELECT COALESCE(SUM(valor_total), 0)
        FROM venda_itens
        WHERE codigo_venda = NEW.codigo_venda
    )
    WHERE codigo = NEW.codigo_venda;

    RETURN NEW;
END;
$$;
 4   DROP FUNCTION public.atualizar_valor_total_venda();
       public          postgres    false    4            �            1259    24670    clientes    TABLE     �   CREATE TABLE public.clientes (
    codigo bigint NOT NULL,
    nome character varying(100) NOT NULL,
    limite_compra numeric(15,2) DEFAULT 0 NOT NULL,
    dia_fechamento_fatura smallint DEFAULT 0 NOT NULL
);
    DROP TABLE public.clientes;
       public         heap    postgres    false    4            �            1259    24669    clientes_codigo_seq    SEQUENCE     �   ALTER TABLE public.clientes ALTER COLUMN codigo ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.clientes_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    4    215            �            1259    32854    produtos    TABLE     �   CREATE TABLE public.produtos (
    codigo bigint NOT NULL,
    descricao character varying(200) NOT NULL,
    preco_unitario numeric(15,2) DEFAULT 0 NOT NULL
);
    DROP TABLE public.produtos;
       public         heap    postgres    false    4            �            1259    32853    produtos_codigo_seq    SEQUENCE     �   ALTER TABLE public.produtos ALTER COLUMN codigo ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.produtos_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    217    4            �            1259    32911    venda_itens    TABLE       CREATE TABLE public.venda_itens (
    codigo_venda bigint NOT NULL,
    codigo_produto bigint NOT NULL,
    preco_unitario numeric(15,2) DEFAULT 0 NOT NULL,
    quantidade numeric(15,3) DEFAULT 0 NOT NULL,
    valor_total numeric(15,2) DEFAULT 0 NOT NULL
);
    DROP TABLE public.venda_itens;
       public         heap    postgres    false    4            �            1259    32899    vendas    TABLE     �   CREATE TABLE public.vendas (
    codigo bigint NOT NULL,
    codigo_cliente bigint NOT NULL,
    data_venda date NOT NULL,
    valor_total numeric(15,2) NOT NULL
);
    DROP TABLE public.vendas;
       public         heap    postgres    false    4            �            1259    32898    vendas_codigo_seq    SEQUENCE     �   ALTER TABLE public.vendas ALTER COLUMN codigo ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.vendas_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    4    219                      0    24670    clientes 
   TABLE DATA                 public          postgres    false    215   $                 0    32854    produtos 
   TABLE DATA                 public          postgres    false    217   �%                 0    32911    venda_itens 
   TABLE DATA                 public          postgres    false    220   �'                 0    32899    vendas 
   TABLE DATA                 public          postgres    false    219   (       $           0    0    clientes_codigo_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.clientes_codigo_seq', 20, true);
          public          postgres    false    214            %           0    0    produtos_codigo_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.produtos_codigo_seq', 20, true);
          public          postgres    false    216            &           0    0    vendas_codigo_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.vendas_codigo_seq', 1, false);
          public          postgres    false    218            {           2606    24676    clientes clientes_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.clientes
    ADD CONSTRAINT clientes_pkey PRIMARY KEY (codigo);
 @   ALTER TABLE ONLY public.clientes DROP CONSTRAINT clientes_pkey;
       public            postgres    false    215                       2606    32905    vendas codigo 
   CONSTRAINT     O   ALTER TABLE ONLY public.vendas
    ADD CONSTRAINT codigo PRIMARY KEY (codigo);
 7   ALTER TABLE ONLY public.vendas DROP CONSTRAINT codigo;
       public            postgres    false    219            }           2606    32859    produtos produtos_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.produtos
    ADD CONSTRAINT produtos_pkey PRIMARY KEY (codigo);
 @   ALTER TABLE ONLY public.produtos DROP CONSTRAINT produtos_pkey;
       public            postgres    false    217            �           2606    32928    venda_itens venda_produto 
   CONSTRAINT     q   ALTER TABLE ONLY public.venda_itens
    ADD CONSTRAINT venda_produto PRIMARY KEY (codigo_venda, codigo_produto);
 C   ALTER TABLE ONLY public.venda_itens DROP CONSTRAINT venda_produto;
       public            postgres    false    220    220            �           2620    32932 *   venda_itens trigger_atualizar_venda_delete    TRIGGER     �   CREATE TRIGGER trigger_atualizar_venda_delete AFTER DELETE ON public.venda_itens FOR EACH ROW EXECUTE FUNCTION public.atualizar_valor_total_venda();
 C   DROP TRIGGER trigger_atualizar_venda_delete ON public.venda_itens;
       public          postgres    false    220    221            �           2620    32930 *   venda_itens trigger_atualizar_venda_insert    TRIGGER     �   CREATE TRIGGER trigger_atualizar_venda_insert AFTER INSERT ON public.venda_itens FOR EACH ROW EXECUTE FUNCTION public.atualizar_valor_total_venda();
 C   DROP TRIGGER trigger_atualizar_venda_insert ON public.venda_itens;
       public          postgres    false    221    220            �           2620    32931 *   venda_itens trigger_atualizar_venda_update    TRIGGER     �   CREATE TRIGGER trigger_atualizar_venda_update AFTER UPDATE ON public.venda_itens FOR EACH ROW EXECUTE FUNCTION public.atualizar_valor_total_venda();
 C   DROP TRIGGER trigger_atualizar_venda_update ON public.venda_itens;
       public          postgres    false    221    220            �           2606    32906    vendas codigo_cliente    FK CONSTRAINT     �   ALTER TABLE ONLY public.vendas
    ADD CONSTRAINT codigo_cliente FOREIGN KEY (codigo_cliente) REFERENCES public.clientes(codigo) NOT VALID;
 ?   ALTER TABLE ONLY public.vendas DROP CONSTRAINT codigo_cliente;
       public          postgres    false    3195    219    215            �           2606    32922    venda_itens codigo_produto    FK CONSTRAINT     �   ALTER TABLE ONLY public.venda_itens
    ADD CONSTRAINT codigo_produto FOREIGN KEY (codigo_produto) REFERENCES public.produtos(codigo) NOT VALID;
 D   ALTER TABLE ONLY public.venda_itens DROP CONSTRAINT codigo_produto;
       public          postgres    false    3197    220    217            �           2606    32917    venda_itens codigo_venda    FK CONSTRAINT     �   ALTER TABLE ONLY public.venda_itens
    ADD CONSTRAINT codigo_venda FOREIGN KEY (codigo_venda) REFERENCES public.vendas(codigo) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;
 B   ALTER TABLE ONLY public.venda_itens DROP CONSTRAINT codigo_venda;
       public          postgres    false    220    219    3199               �  x����n�0��~����@����G	�'�\=�$�&@�)���ɩϑ+W6���?gg&ߕYq�|w��[_]��F��S�Ǭ(��|���m��|Ϯo	_���4�����Ɛ&Ir�$c��o���O~��X%����>��%>	�U����!�2�ŔI�r�kt���jE��"
��g���$^����������r-�A�������������P��48/#]2��@��ie`e�J7���2�"y�ac߆æ17.\Pn�'�T�n	/��Oe�=V������<:?��ӡN����*����Up�T�MO��pAs��M�O�=�4��pK�p��Z��.�v��{�]�t9����Sk���n��������So�T�����;:K.����UpM�Dq$7��z�����Sl�G�󲗁F�~~X(���չ�i�G�?��*         �  x���A��@E�>E)�I�	6(+;�6��- e�@%��F83�I��#�b�&)R�͆�ϯ���c�9����PԼ�Z%����.Q�ƛ����K�G	\V��џoo�{���*���5�����m�
�wf��%r��
!�J���/�%�߶|�?'�Q�XH��ў���ٞ�.壟�����Vh*�'t�e�*		������ݚ&���t�b�>t6�w\��LMYx+�}��+�X��R��4�]�V`*>�X���u��&���)9$rܴ
�NRԓ����A�\
VӐp
���dS}����X���f�/�I:C��rtisV��êmk~f�P0ED��=��#��M��l�Yg>^�B�b�\�&2��\�nlJ׀j�����rm�;�N�םB~��Bo���zFQ)~EXx�	𦈇n�~�s�JHp��L]г��
�L<�h�ݬ�p�)��y��{8�#\]���pq��8�C��{"�wc6�i"�         
   x���             
   x���         