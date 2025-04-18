import React, { useState } from 'react';
import { createPoll } from '../api/api';

const AdminPollCreate: React.FC = () => {
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [options, setOptions] = useState<{text: string}[]>([]);
    const [message, setMessage] = useState<{ type: 'success' | 'error'; text: string } | null>(null);

    const addOption = () => setOptions([...options, {text: ''}]);

    const handleSubmit = async () => {
        try {
            const error = validatePollInputs(title, description, options);
            if (error) {
                showError(error);
                return;
            }
            await createPoll({ title, description, options });
            setMessage({ type: 'success', text: 'Poll created successfully!' });
            setTitle('');
            setDescription('');
            setOptions([]);
        } catch (error) {
            setMessage({ type: 'error', text: 'Failed to create poll. Please try again.' });
        }
    };

    const showError = (text: string) => {
        setMessage({ type: 'error', text });
        setTimeout(() => setMessage(null), 2000);
    };

    const validatePollInputs = (title: string, description: string, options: { text: string }[]) => {
        if (!title?.trim()) return 'Title is required.';
        if (!description?.trim()) return 'Description is required.';
        if (options.length === 0) return 'Please add at least one option.';
        return null;
    };

    return (
        <div className="bg-inherit flex items-center justify-center px-4">
            <div className="w-full max-w-2xl bg-white p-8 rounded-lg shadow-lg">
                <h2 className="text-2xl font-bold text-gray-800 mb-6 text-center">Create New Poll</h2>

                {message && (
                    <div
                        className={`mb-4 p-3 rounded text-sm ${
                            message.type === 'success' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
                        }`}
                    >
                        {message.text}
                    </div>
                )}

                <input
                    className="border border-gray-300 rounded px-4 py-2 w-full mb-4"
                    placeholder="Poll Title"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                />

                <textarea
                    className="border border-gray-300 rounded px-4 py-2 w-full mb-4"
                    placeholder="Poll Description"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                />

                {options.map((opt, index) => (
                    <div key={index} className="flex items-center mb-2">
                        <input
                            className="border border-gray-300 rounded px-4 py-2 w-full"
                            placeholder={`Option ${index + 1}`}
                            value={opt.text}
                            onChange={(e) => {
                                const newOpts = [...options];
                                newOpts[index].text = e.target.value;
                                setOptions(newOpts);
                            }}
                        />
                        <button
                            type="button"
                            onClick={() => {
                                const newOpts = options.filter((_, i) => i !== index);
                                setOptions(newOpts);
                            }}
                            className="ml-2 text-red-500 hover:text-red-700"
                        >
                            ❌
                        </button>
                    </div>
                ))}

                <div className="flex justify-between mt-4">
                    <button
                        className="bg-indigo-500 hover:bg-indigo-600 text-white px-4 py-2 rounded transition"
                        onClick={addOption}
                    >
                        Add New Option
                    </button>

                    <button
                        className="bg-green-500 hover:bg-green-600 text-white px-4 py-2 rounded transition"
                        onClick={handleSubmit}
                    >
                        Create Poll
                    </button>
                </div>
            </div>
        </div>
    );
};

export default AdminPollCreate;