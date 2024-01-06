open Unix
open Libcsv

(*bonus : ma fonction pour recuerer un élement a un certain index 
(peut peut etre etre refait en utilisant les fonctions de la bibliotheque List ) *)

let rec recuperer_element l index = match l with
  |[] -> failwith "liste vide"
  |e :: rl -> if index = 0 then e else recuperer_element rl (index-1);;


(* modification_csv chargé de modifier la matrice donnée
liste_liste_csv représente une matrice du csv : une liste des lignes du csv, 
une ligne repésentée par une  liste de chaine de charactères : a l'index 0 il y a le premiere élément de la ligne ect...
 *)

let rec modification_csv liste_liste_csv = 
    match liste_liste_csv with 
    | [] -> []
    | h::tl -> match h with 
        |[] -> [] :: (modification_csv tl)
        |name::_ -> [name]::(modification_csv tl)
;;

let main_csv () =
  let () = Format.printf "\nDebut du programme ocaml...\n" in
  let path_in = Libunix.get_csv_file "fichier.csv" in
  let path_out = Libunix.get_csv_file "fichier_out.csv" in
  let csv_in = Libcsv.load_csv path_in in
  let csv_out = modification_csv csv_in in 
  let nl, nc = Libcsv.lines_columns csv_in in
  let () = Format.printf "Ecriture d'un CSV de taille (%d x %d) dans: %s\n" nl nc path_out in
  Libcsv.save_csv path_out csv_out


(* Exécute les procédures précédentes *)
let () = main_csv ();;
