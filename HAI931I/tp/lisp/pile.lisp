;; (load "pile.lisp")

(defclass memo-object (standard-object) 
  ()
  (:metaclass standard-class))

(defclass memo-class (standard-class)
  ((EnsInstances 
    :initform nil
    :accessor get-memo-class))
  (:metaclass standard-class))

(defmethod make-instance ((mc memo-class) &rest args)
  (let ((x (call-next-method)))
    (setf (get-memo-class mc)
      (cons x (get-memo-class mc)))
    x))

(defmethod validate−superclass ((cl memo−class) (sup standard−class))
  (eq 'memo−object (class−name sup)))

(defmethod validate−superclass ((cl memo−class) (sup memo−class)) t)

(defmethod validate−superclass ((cl standard−class) (sup memo−class)) ())

(setf pile (make-instance 'memo-class))