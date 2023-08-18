export function Modal({ isOpen, onClose, children }) {
  if (!isOpen) return null

  return (
    <div className='fixed inset-0 z-50 flex items-center justify-center backdrop-blur-sm backdrop-filter'>
      <div className='z-10 w-9/12 rounded-lg p-6'>
        <button
          className='mb-4 rounded bg-red-500 px-4 py-2 text-white'
          onClick={onClose}
        >
          Cancelar registro
        </button>
        {children}
      </div>
    </div>
  )
}
