;; Execution: (load "tp4.lisp")


;; ----------------------------------------
;; Class point
;; ----------------------------------------
(defclass point (standard-object) (
  (x :initform 1
      :initarg :x
      :accessor getx)
  (y :initform 2
      :initarg :y
      :accessor gety)
  (z :allocation :instance
      :initarg :z
      :accessor getz)
))

;; Init a new point
(setf p1 (make-instance 'point :x 19 :y 12))
(format T "[+] Init new point ~%")
(format T "[i] x: ~d ~%" (getx p1))
(format T "[i] y: ~d ~%" (gety p1))

;; Set x value
(setf (getx p1) 33)
(format T "[i] setf x: ~d ~%" (getx p1))

;; ----------------------------------------
;; Class person
;; ----------------------------------------
(defclass person ()(
  (name :initarg :name
        :accessor getName)
  (species
    :initform 'homo-sapiens
    :accessor getSpecies
    :allocation :class
  )
))

(defclass child (person) (
  (can-walk-p
    :initarg :can-walk ;; instantiation
    :accessor can-walk-p
    :initform T)
))

(setf p1 (make-instance 'person :name "Pierre"))
(format T "~%[+] Init person ~%")
(format T "[i] name: ~s ~%" (getName p1))
(format T "[i] species: ~s ~%" (getSpecies p1))

(setf c1 (make-instance 'child :name "Lisa"))
(format T "~%[+] Init child ~%")
(format T "[i] name: ~s ~%" (getName c1))
(format T "[i] species: ~s ~%" (getSpecies c1))

(format T "[i] can-walk: ~s ~%" (can-walk-p c1))
