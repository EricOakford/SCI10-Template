;;; Sierra Script 1.0 - (do not remove this comment)\
;********************************************************************
;***
;***	VIEWS.SH
;***	  View defines
;***
;********************************************************************

;Ego views
(define	vEgo		0)
(define vEgoTalk	10)

(define vSpeedBox 99)

(define vInvItems	700)
(define vIconBar	900)
	(enum
		lWalkIcon
		lLookIcon
		lDoIcon
		lTalkIcon
		lItemIcon
		lInvIcon
		lControlIcon
		lHelpIcon
		lSmellIcon
		lTasteIcon
		lDisabledIcon
	)	
(define vInvIcons 901)
	(enum
		lInvHand
		lInvHelp
		lInvLook
		lInvOK
		lInvSelect
	)	
(define vControlIcons 947)
	(enum
		lSliderText
		lControlFixtures
		lSaveButton
		lRestoreButton
		lRestartButton
		lQuitButton
		lAboutButton
		lHelpButton
		lOKButton
	)