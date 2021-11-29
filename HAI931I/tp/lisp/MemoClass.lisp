; thibaudcolas gitub
; https://github.com/thibaudcolas/metaprogramming/blob/master/clos/memoclass/memoclass.lisp

; Mémo-classes : classes qui enregistrent leurs instances.
; ---------------------------------------------------------------------

; Définition des classes.
; ---------------------------------------------------------------------

; Mémo-classe.
(defclass memo-class (standard-class)
  (
   (instance-set :accessor class-instance-set
                 :initform nil)
   )
  (:metaclass standard-class)
  )

; Mémo-objet. La super classe des classes qui mémorisent la liste de leurs instances
(defclass memo-object (standard-object)
  ()
  (:metaclass standard-class)
  )

; Définition de la méthode make-instance de memo-class.
; ---------------------------------------------------------------------

(defmethod make-instance ((mc memo-class) &rest initargs)
  (let ((instance (call-next-method)))
    ; On ajoute instance à notre ensemble d'instances.
    (setf (class-instance-set mc)
          (cons instance (class-instance-set mc))
          )
    ; On renvoit l'instance créée.
    instance)
  )

; Validations d'héritage avec validate-superclass.
; ---------------------------------------------------------------------

; Si memo-class hérite de memo-class, tout va bien.
(defmethod validate-superclass ((memo memo-class) (sup memo-class))
  T
  )

; Si c'est l'inverse, rien ne va plus.
(defmethod validate-superclass ((std standard-class)(memo memo-class))
  nil
  )

; Si on hérite d'une standard-class, il faut tester qu'elle soit memo-object
(defmethod validate-superclass ((memo memo-class) (sup standard-class))
  ; Ne devrait-ce pas être un test pour 'memo-class ?
  (eq (class-name sup) 'memo-object)
  )

; Libération des instances pour le GC.
; ---------------------------------------------------------------------

; Libération pour memo-class.
(defmethod free-instance ((mc memo-class) item)
  (setf (class-instance-set mc)
        (delete item (class-instance-set mc))
        )
  )

; Libération pour memo-object.
(defmethod free-object ((mo memo-object))
    (free-instance (class-of mo) mo)
)

; Tests.
; ---------------------------------------------------------------------

; Récupération des instances.
(defun get-instances (mc)
  (class-instance-set (find-class mc))
  )

(defclass A (memo-object) (()) (:metaclass memo-class))
(defclass B (memo-object) (()) (:metaclass memo-class))

(setf b (make-instance 'b))
(setf bb (make-instance 'b))

(setf a (make-instance 'a))

(print b)
(print bb)
(print (get-instances 'b))

(print 'end)
