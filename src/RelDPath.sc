;;; Sierra Script 1.0 - (do not remove this comment)
(script# RELDPATH)
(include system.sh) (include sci2.sh)
(use DPath)


(class RelDPath of DPath
   (method (setTarget)
      (if (!= (points at: value) PATHEND)
         (+= x (points at: value))
         (+= y (points at: (++ value)))
         (++ value)
      )
   )
);RelDPath

