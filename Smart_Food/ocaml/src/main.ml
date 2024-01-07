open Unix
open Libcsv

(* ###### TYPES ###### *)

type unit = G | KG | L | U;;

type date = Date of int*int*int;; (* AAAA * MM * JJ *)

type product = {name : string; unit : unit; date : date; q : float};;



(* ###### CSV -> product list ###### *)
let string_to_date (str : string) : date =
  match String.split_on_char '-' str with
  | [yyyy; mm; dd] ->
      Date(int_of_string yyyy, int_of_string mm, int_of_string dd)
  | _ -> failwith "Format de date invalide"
;;


let string_to_unit (str : string) =
    match str with 
    | "G" -> G
    | "KG" -> KG
    | "L" -> L
    | "U" -> U
    | _ -> failwith "Format Unite invalide"
;;


let line_to_product (line : string list) : product =
  match line with
  | [name; unit_str; date_str; q_str] ->
      {name = name; 
       unit = string_to_unit unit_str;
       date = string_to_date date_str; 
       q = float_of_string q_str}
  | _ -> failwith "Format de ligne invalide"
;;

let csv_to_list_product liste_liste_csv = 
    
    let rec aux csv (res : product list) =
        match csv with
        | [] -> res
        | l::next -> aux next ((line_to_product l)::res)
    in match liste_liste_csv with
    | []| [_] -> []
    | _::l -> aux l []
;;


(* ###### Affichage des types ###### *)

let print_date (Date(yyyy, mm, dd)) =
  Printf.printf "%d-%02d-%02d" yyyy mm dd;;

let print_unit u =
  match u with
  | G -> Printf.printf "G"
  | KG -> Printf.printf "KG"
  | L -> Printf.printf "L"
  | U -> Printf.printf "U";;

let print_product (p : product) =
  Printf.printf " - %s (" p.name;
  print_unit p.unit;
  Printf.printf ") -> ";
  print_date p.date;
  Printf.printf " : %.2f\n" p.q;;

let rec print_product_list = function
  | [] -> ()
  | p::ps -> 
      print_product p;
      print_product_list ps
;;




(* ###### Tri de l'historique ###### *)

(*
    < 0 si d1 < d2
    = 0 si d1 = d2
    > 0 si d1 > d2
*)
let compare_date d1 d2 = 
    let (Date(y1,m1,d1),Date(y2,m2,d2)) = (d1,d2) in
    if(y1-y2 <> 0) then y1-y2 else
    if(m1-m2 <> 0) then m1-m2 else d1-d2
;;

let date_is_ordered_increasing d1 d2 =
    (compare_date d1 d2) <= 0
;;

let date_is_ordered_decreasing d1 d2 =
    (compare_date d1 d2) >= 0
;;

let rec add_to_sorted_list e l is_ordered = 
    match l with
    | [] -> [e]
    | hd::tl -> if(is_ordered e.date hd.date) then e::l else hd :: (add_to_sorted_list e tl is_ordered)
;;

let sort_list l is_ordered = 
    List.fold_left  (fun r e -> add_to_sorted_list e r is_ordered) [] l


(* ###### Stock d'un produit ###### *)

let get_stock_of_product stock name = 
    let equal  = (=) name in
    List.filter (fun p -> equal p.name) stock


let quantity_of_product stock name =
    let stock_product = get_stock_of_product stock name in
    List.fold_left (fun r e -> r +. e.q) 0. stock_product


(* ##### calcul des dates ##### *)

let get_last_day =
  let time_now = Unix.time () in
  let time_yesterday = time_now -. 86400.0 in (* Soustrait 86400 secondes (1 jour) *)
  let tm = Unix.localtime time_yesterday in
  Date (tm.Unix.tm_year + 1900, tm.Unix.tm_mon + 1, tm.Unix.tm_mday)

let get_last_month =
  let time_now = Unix.time () in
  let tm = Unix.localtime time_now in
  let last_month = if tm.Unix.tm_mon = 0 then 11 else tm.Unix.tm_mon - 1 in
  let last_year = if tm.Unix.tm_mon = 0 then tm.Unix.tm_year - 1 else tm.Unix.tm_year in
  Date (last_year + 1900, last_month + 1, tm.Unix.tm_mday)

let get_last_year =
  let time_now = Unix.time () in
  let tm = Unix.localtime time_now in
  Date (tm.Unix.tm_year + 1900 - 1, tm.Unix.tm_mon + 1, tm.Unix.tm_mday)

let get_list_after_date (d : date) stock =
    List.filter (fun e -> date_is_ordered_increasing d e.date) stock




(* ###### Programme principal ###### *)

let load_historical = 
    let path_in = Libunix.get_csv_file "history.csv" in
    let csv_in = Libcsv.load_csv path_in in
    let l = csv_to_list_product csv_in in
    sort_list l date_is_ordered_increasing

let display_csv () =
  let list_product = load_historical in
  let sorted_list_product = sort_list list_product date_is_ordered_increasing in 
  print_product_list sorted_list_product

let get_quantity product_name =
  let list_product = load_historical in
  let total = quantity_of_product list_product product_name in 
  Format.printf "\nQuantité de %s consommé(es) : %0.2f\n\n" product_name total


let display_last_day () =
    let list_product = load_historical in
    let list_last_day = get_list_after_date get_last_day list_product in
    print_product_list list_last_day

let display_last_month () =
    let list_product = load_historical in
    let list_last_month = get_list_after_date get_last_month list_product in
    print_product_list list_last_month

let display_last_year () =
    let list_product = load_historical in
    let list_last_year = get_list_after_date get_last_year list_product in
    print_product_list list_last_year

let main () =
  let argc = Array.length Sys.argv in
  match argc with
  | 1 -> 
      Printf.printf "Aucun argument spécifié.\n"
  | _ -> 
      match Sys.argv.(1) with
      | "DISPLAY" -> display_csv ()
      | "QUANTITY" -> 
          if argc > 2 then get_quantity Sys.argv.(2)
          else Printf.printf "produit non spécifié pour QUANTITY.\n"
      | "LAST_DAY" -> display_last_day ()
      | "LAST_MONTH" -> display_last_month ()
      | "LAST_YEAR" -> display_last_year ()
      | _ -> Printf.printf "Argument non reconnu.\n"

(* Exécute la fonction principale *)
let () = main ()